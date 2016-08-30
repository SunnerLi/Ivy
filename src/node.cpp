#include <iostream>
#include "node.h"
#include "word.h"
using namespace std;

// Constructor
Node::Node(Word _){
    word = _;
    left = NULL;
    right = NULL;
}

Node::Node(char *eng, char *chi){
    Word _w(eng, chi);
    word = _w;
    left = NULL;
    right = NULL;
}

// Set method
bool Node::setLeftChild(Word _){
    Node node(_);
    *left = node;
}

bool Node::setLeftChild(char *eng, char *chi){
    Node node(eng, chi);
    *left = node;
}

bool Node::setRightChild(Word _){
    Node node(_);
    *right = node;
}

bool Node::setRightChild(char *eng, char *chi){
    Node node(eng, chi);
    *right = node;
}

// Get method
Node Node::getLeftChild(){
    return *left;
}

Node Node::getRightChild(){
    return *right;
}