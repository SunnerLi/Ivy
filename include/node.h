#ifndef _NODE_H
#define _NODE_H

#include <iostream>
#include "word.h"
using namespace std;

class Node{
    public:
        // Constructor
        Node(){}
        Node(Word _);
        Node(char *eng, char *chi);

        // Set method
        bool setLeftChild(Word _);
        bool setLeftChild(char *eng, char *chi);
        bool setRightChild(Word _);
        bool setRightChild(char *eng, char *chi);

        // Get method
        Node getLeftChild();
        Node getRightChild();

        // Other method

    private:
        Word word;
        Node* left;
        Node* right;
};

#endif