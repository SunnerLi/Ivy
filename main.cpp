#include <iostream>
#include "tree.h"
#include "word.h"

using namespace std;

int main(){
    
    Tree t("apple", "蘋果");
    t.insert("cat", "貓");
    t.insert("dog", "狗");
    t.del("apple");
    t.reassemble();
    t.inorderTreeWalk();

    Tree t2 = t.buildTreeView(1, TREEVIEW_WATCH);
    t2.inorderTreeWalk();
    
    return 0;
}
