#include <iostream>
#include "word.h"

using namespace std;

int main(){
    Word w("人", "hi");
    for(int i=0; i<4; i++){
        cout << int(w.getChinese()[i]) << ' ';
    }
    cout << endl;
    return 0;
}
