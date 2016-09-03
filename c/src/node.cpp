#include <iostream>
#include <cmath>
#include <cstring>
#include "node.h"
#include "word.h"
#include "err.h"
using namespace std;

// ----- Constructor -----
Node::Node(const Node& _){
    word = _.getWord();
    left = _.getLeftChild();
    right = _.getRightChild();
}

Node::Node(Word _){
    word = _;
    left = 0;
    right = 0;
}

Node::Node(char *eng, char *chi){
    Word _w(eng, chi);
    word = _w;
    left = 0;
    right = 0;
}

// ----- Set method ------
void Node::setWord(Word _){
    word = _;
}

void Node::setWord(char *eng, char *chi){
    Word _(eng, chi);
    word = _;
}

void Node::setLeftChild(Word _){
    left = new Node(_);
}

void Node::setLeftChild(char *eng, char *chi){
    left = new Node(eng, chi);
}

void Node::setLeftChild(Node node){
    left = new Node(node.getWord());
    left->left = node.getLeftChild();
    left->right = node.getRightChild();
}

void Node::setRightChild(Word _){
    right = new Node(_);
}

void Node::setRightChild(char *eng, char *chi){
    right = new Node(eng, chi);
}

void Node::setRightChild(Node node){
    right = new Node(node.getWord());
    right->left = node.getLeftChild();
    right->right = node.getRightChild();
}

// ----- Get method -----
Word Node::getWord() const{
    return word;
}

Node* Node::getLeftChild() const{
    return left;
}

Node* Node::getRightChild() const{
    return right;
}

// ----- Tree method -----
/*
 *  In-order tree walk to show the tree structure
 */
void Node::inorderTreeWalk(){   
    if(left != NULL)
        left -> inorderTreeWalk();    
    cout << word.getEnglish() << endl;
    if(right != NULL)
        right -> inorderTreeWalk();
}

/*
 *  In-order tree walk to collect whole element as queue
 *
 *  Arg:    queue object reference
 */
void Node::buildTreeWalkQ(queue<Word> &q){
    if(left != NULL)
        left -> buildTreeWalkQ(q);    
    q.push(word);
    if(right != NULL)
        right -> buildTreeWalkQ(q);
}

/*
 *  In-order tree walk to collect whole element as queue
 *  The element should meet the condition (and value)
 *
 *  Arg:    condition value, condition type and queue object reference
 */
void Node::buildTreeView(int value, int type, queue<Word> &q){
    if(left != NULL)
        left -> buildTreeView(value, type, q);
    if(type == TREEVIEW_WATCH && word.getNumberOfWatch() >= value)
        q.push(word);
    else if(type == TREEVIEW_WRONG && word.getNumberOfWrong() >= value)
        q.push(word);
    if(right != NULL)
        right -> buildTreeView(value, type, q);
}

/*
 *  Delete the element from the tree
 *
 *  Arg:    The word object that want to delete
 *  Ret:    If successfully delete
 */
bool Node::del(Word _){
    if(word.getChinese() == _.getChinese() &&
        word.getEnglish() == _.getEnglish()){
        this->setWord(__wordErr);
        return true;
    }
    else{
        bool hadFind = false;
        if(left != 0)
            hadFind = hadFind || left -> del(_);
        if(right != 0)
            hadFind = hadFind || right -> del(_);
        return hadFind;
    }
}

/*
 *  Delete the element from the tree
 *
 *  Arg:    The english or chinese string that want to delete
 *  Ret:    If successfully delete
 */
bool Node::del(char* _){
    if(word.getChinese() == _ || word.getEnglish() == _){
        this->setWord(__wordErr);
        return true;
    }
    else{
        bool hadFind = false;
        if(left != 0)
            hadFind = hadFind || left -> del(_);
        if(right != 0)
            hadFind = hadFind || right -> del(_);
        return hadFind;
    }
}

/*
    Judge the direction about next tree walk

    Arg:    Word object
    Ret:    Left or right
*/
int Node::direction(Word _){
    int length = min(sizeof(_.getEnglish()), sizeof(this->word.getEnglish()));
    for(int i=0; i<length; i++){
        int cWalk = int(_.getEnglish()[i]);
        int cThis = int(this->word.getEnglish()[i]);
        if(cWalk == cThis)
            continue;
        else if(cWalk > cThis)
            return RIGHT;
        else
            return LEFT;
    }
    return LEFT;
}

/*
 *  Judge the direction about next tree walk
 *
 *  Arg:    Node object
 *  Ret:    Left or right
 */
int Node::direction(Node __){
    Word _ = __.getWord();
    return direction(_);
}

/*
 *  Judge if the english or chinese string exist recursively
 *
 *  Arg:    The string want to examine
 *  Ret:    If it's exist
 */
bool Node::isExist(char* _){
    if( !strcmp(word.getEnglish(), _) )
        return true;
    else{
        bool hadExist = false;
        if(left != 0)
            hadExist = hadExist || left -> isExist(_);
        if(right != 0)
            hadExist = hadExist || right -> isExist(_);
        return hadExist;
    }
}

// ----- Overloadding operator -----
Node& Node::operator=(const Node &node){
    this->setWord(node.getWord());
    if(node.getLeftChild() != 0)
        left = node.getLeftChild();
    else
        left = 0;
    if(node.getRightChild() != 0)
        right = node.getRightChild();
    else
        right = 0;
    return *this;
}