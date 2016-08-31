#include <iostream>
#include <queue>
#include <cstring>
#include "word.h"
#include "tree.h"
#include "err.h"
using namespace std;

// ----- Constructor -----
Tree::Tree(char* eng, char* chi){
    root = new Node(eng, chi);
}

Tree::Tree(Word _){
    root = new Node(_);
}

Tree::Tree(Node _){
    root = new Node(_);
}

// ----- Method -----
bool Tree::insert(char* eng, char* chi){
    Word _(eng, chi);
    return insert(_);
}

bool Tree::insert(Word _){
    if(!isExist(_.getEnglish())){
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
    return false;       // If had exist, discard
}

/*
 *  Delete the element from the tree
 *
 *  Arg:    The english and chinese string that want to delete
 *  Ret:    If successfully delete
 */
bool Tree::del(char* eng, char* chi){
    if(root != 0){
        // If exist in root
        if(root->getWord().getEnglish() == eng &&
            root->getWord().getChinese() == chi){
            root->setWord(__wordErr);
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

/*
 *  Delete the element from the tree
 *
 *  Arg:    The english or chinese string that want to delete
 *  Ret:    If successfully delete
 */
bool Tree::del(char* _){
    if(root != 0){
        // If exist in root
        if( strcmp(root->getWord().getEnglish(), _) ||
            strcmp(root->getWord().getChinese(), _)){
            root->setWord(__wordErr);
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

/*
 *  Delete the element from the tree
 *
 *  Arg:    The word object that want to delete
 *  Ret:    If successfully delete
 */
bool Tree::del(Word _){
    return this->del(_.getEnglish(), _.getChinese());
}

/*
 *  Re-assemble the tree to delete the space-node which would generate after deleting
 */
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

/*
 *  In-order tree walk to show the tree structure
 */
void Tree::inorderTreeWalk(){
    Node *curr = root;
    if(curr -> getLeftChild() != 0)
        curr -> getLeftChild() -> inorderTreeWalk();
    cout << curr -> getWord().getEnglish() << endl;
    if(curr -> getRightChild() != 0)
        curr -> getRightChild() -> inorderTreeWalk();
}

/*
 *  Build a temporary tree which meet the corresponding confition
 *
 *  Arg:    Condition value and condition type
 *  Ret:    The new tree object
 */
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
        return *(new Tree(__wordErr));
    }
}

/*
 *  Judge if the english or chinese string exist recursively
 *
 *  Arg:    The english and chinese string want to examine
 *  Ret:    If it's exist
 */
bool Tree::isExist(char* eng, char* chi){
    return isExist(eng);
}

/*
 *  Judge if the english or chinese string exist recursively
 *
 *  Arg:    The english or chinese string want to examine
 *  Ret:    If it's exist
 */
bool Tree::isExist(char* _){
    Node* curr = root;
    if( !strcmp(curr -> getWord().getEnglish(), _))
        return true;
    else{
        bool hadExist = false;
        if(curr -> getLeftChild() != 0)
            hadExist = hadExist || curr -> getLeftChild() -> isExist(_);
        if(curr -> getRightChild() != 0)
            hadExist = hadExist || curr -> getRightChild() -> isExist(_);
        return hadExist;
    }
}