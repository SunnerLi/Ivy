#include <iostream>
#include "word.h"
#include "node.h"
#define TREEVIEW_WATCH 0
#define TREEVIEW_WRONG 1

class Tree{
    public:
        // Constructor
        Tree(){}
        Tree(char* eng, char* chi);
        Tree(Word _);

        // Method
        bool insert(char* eng, char* chi);
        bool insert(Word _);
        bool del(char* eng, char* chi);
        bool del(char* _);
        bool del(Word _);
        void reassemble();
        void inorderTreeWalk();
        Tree buildTreeView(int value, int type);
        Tree buildTreeView(Word _); 
    private:
        Node root;
};