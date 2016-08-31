#include <iostream>
#include "tree.h"
#include "word.h"

using namespace std;

// Exist test
void TestExist(Tree t){
    char testing[4][20] = {"apple", "蘋果", "egg", "蛋"};
    cout << "apple exist?  Ans: " 
         << t.isExist(testing[0], testing[1]) << endl;
    cout << "egg   exist?  Ans: " 
         << t.isExist(testing[2]) << endl;
}

void TestBuildView(Tree t){
    Tree t2 = t.buildTreeView(1, TREEVIEW_WATCH);
    t2.inorderTreeWalk();
}

int main(){
    char engString[3][20] = {"apple", "cat", "dog"};
    char chiString[3][20] = {"蘋果", "貓", "狗"};

    Tree t(engString[0], chiString[0]);
    t.del(engString[0]);
    for(int i=0; i<3; i++)
        t.insert(engString[i], chiString[i]);
    t.reassemble();
    t.inorderTreeWalk();
    return 0;
}
