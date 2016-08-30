#include <iostream>
#include <queue>
#include <cstring>
#include "word.h"
#include "tree.h"
using namespace std;

// Constructor
Tree::Tree(char* eng, char* chi){
    root = new Node(eng, chi);
}

Tree::Tree(Word _){
    root = new Node(_);
}

Tree::Tree(Node _){
    root = new Node(_);
}

// Method
bool Tree::insert(char* eng, char* chi){
    Word _(eng, chi);
    return insert(_);
}

bool Tree::insert(Word _){
    Node node(_);
    Node *curr;
    Node *prev;
    curr = root;
    int finalDir = -1;
    
    while(curr != 0){
        prev = curr;
        finalDir = curr->direction(node);
        if( finalDir == LEFT)
            curr = curr->getLeftChild();
        else
            curr = curr->getRightChild();
    }
    
    if(finalDir == LEFT){
        prev->setLeftChild(node);
    }
    else{
        prev->setRightChild(node);
    }
    return true;
}

bool Tree::del(char* eng, char* chi){
    if(root != 0){
        // If exist in root
        if(root->getWord().getEnglish() == eng &&
            root->getWord().getChinese() == chi){
            Word _("    ", "錯誤");
            root->setWord(_);
            return true;
        }
        
        // inorder tree walk accounting for it
        Word _(eng, chi);
        Node* curr;
        curr = root;
        bool hadFind = false;
        if(curr -> getLeftChild() != 0)
            hadFind = hadFind || curr -> getLeftChild() -> del(_);
        if(curr -> getRightChild() != 0)
            hadFind = hadFind || curr -> getRightChild() -> del(_);
        return hadFind;
    }
    return false;
}

bool Tree::del(char* _){
    if(root != 0){
        // If exist in root
        if( strcmp(root->getWord().getEnglish(), _) ||
            strcmp(root->getWord().getChinese(), _)){
            Word _("    ", "錯誤");
            root->setWord(_);
            return true;
        }

        // inorder tree walk accounting for it
        Node* curr;
        curr = root;
        bool hadFind = false;
        if(curr -> getLeftChild() != 0)
            hadFind = hadFind || curr -> getLeftChild() -> del(_);
        if(curr -> getRightChild() != 0)
            hadFind = hadFind || curr -> getRightChild() -> del(_);
    }
}

bool Tree::del(Word _){
    return this->del(_.getEnglish(), _.getChinese());
}

void Tree::reassemble(){
    // Copy the original tree
    queue<Word> q;
    Node *curr = root;
    if(curr -> getLeftChild() != 0)
        curr -> getLeftChild() -> buildTreeWalkQ(q);
    q.push(curr->getWord());
    if(curr -> getRightChild() != 0)
        curr -> getRightChild() -> buildTreeWalkQ(q);
    
    // Find the new root
    Word word;
    do{
        word = q.front();
        q.pop();
        if(word == 0){
        }else
            break;
    }while(q.size());
    root = new Node(word);

    // Build the new tree
    while(q.size()!=0){
        word = q.front();
        q.pop();
        if(word == 0)
            continue;
        else
            insert(word);
    }
}

void Tree::inorderTreeWalk(){
    Node *curr = root;
    if(curr -> getLeftChild() != 0)
        curr -> getLeftChild() -> inorderTreeWalk();
    cout << curr -> getWord().getEnglish() << endl;
    if(curr -> getRightChild() != 0)
        curr -> getRightChild() -> inorderTreeWalk();
}

Tree Tree::buildTreeView(int value, int type){
    // Copy the original tree
    queue<Word> q;
    Node *curr = root;
    if(curr -> getLeftChild() != 0)
        curr -> getLeftChild() -> buildTreeView(value, type, q);

    if(type == TREEVIEW_WATCH && curr->getWord().getNumberOfWatch() >= value)
        q.push(curr->getWord());
    else if(type == TREEVIEW_WRONG && curr->getWord().getNumberOfWrong() >= value)
        q.push(curr->getWord());
    
    if(curr -> getRightChild() != 0)
        curr -> getRightChild() -> buildTreeView(value, type, q);

    // Build the new tree
    if(q.size()){
        Tree* newT = new Tree(q.front());
        q.pop();
        while(q.size()!=0){
           newT->insert(q.front());
           q.pop();
        }
        return *newT;
    }else{
        Word w("    ", "錯誤");
        return *(new Tree(w));
    }
}