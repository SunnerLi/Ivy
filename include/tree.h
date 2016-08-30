#include <iostream>
#include "word.h"
#include "node.h"


class Tree{
    public:
        // Constructor
        Tree(){}
        Tree(char* eng, char* chi);
        Tree(Word _);
        Tree(Node _);

        // Method
        bool insert(char* eng, char* chi);
        bool insert(Word _);
        bool del(char* eng, char* chi);
        bool del(char* _);
        bool del(Word _);
        void reassemble();
        void inorderTreeWalk();
        Tree buildTreeView(int value, int type);
    private:
        Node* root;
};