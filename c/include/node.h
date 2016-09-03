#ifndef _NODE_H
#define _NODE_H

#include <iostream>
#include <queue>
#include "word.h"
#define LEFT 2
#define RIGHT 3
using namespace std;

class Node{
    public:
        // Constructor
        Node(){}
        Node(const Node& _);
        Node(Word _);
        Node(char *eng, char *chi);

        // Set method
        void setWord(Word _);
        void setWord(char *eng, char *chi);
        void setLeftChild(Word _);
        void setLeftChild(char *eng, char *chi);
        void setLeftChild(Node node);
        void setRightChild(Word _);
        void setRightChild(char *eng, char *chi);
        void setRightChild(Node node);

        // Get method
        Word getWord() const;
        Node* getLeftChild() const;
        Node* getRightChild() const;

        // Tree method
        void inorderTreeWalk();
        void buildTreeWalkQ(queue<Word> &q);
        void buildTreeView(int value, int type, queue<Word> &q);
        bool del(Word _);
        bool del(char* _);
        int direction(Word _);
        int direction(Node __);
        bool isExist(char* _);

        // Overloadding operator
        Node& operator=(const Node &node);

    private:
        Word word;
        Node* left;
        Node* right;
};

#endif