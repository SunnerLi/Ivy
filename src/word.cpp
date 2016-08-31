#include <iostream>
#include <cstring>
#include "word.h"
#include "err.h"
using namespace std;

// ----- Constructor Implementation -----
Word::Word(char* eng, char* chi){
    if(this->isEnglish(eng))
        this->setEnglish(eng);
    else{
        cout << "The first string should be in english..." << endl
             << "The input is: " << eng << endl
             << "set as 'None'..." << endl;
        this->setEnglish(__enErr);
    }
    if(this->isChinese(chi))
        this->setChinese(chi);
    else{
        cout << "第二個字串須為中文 ..." << endl
             << "輸入為： " << chi << endl
             << "設為 '錯誤' ..." << endl;
        this->setEnglish(__chErr);
    }

    // Set default value
    Date d;
    d.Now();
    this->setDate(d);
    this->setNumberOfWrong(0);
    this->setNumberOfWatch(0);
}

/*
 *  Insert Method
 *
 *  Arg:    the value want to set
 *  Ret:    If successfully set (Or none)
 */
void Word::setEnglish(const char* eng){
    strcpy(englist, eng);
}

void Word::setChinese(const char* chi){
    strcpy(chinese, chi);
}

void Word::setDate(Date d){
    date = d;
}

bool Word::setNumberOfWrong(int _){
    if(_>0){
        numberOfWrong = _;
        return true;
    }
    return false;
}

bool Word::setNumberOfWatch(int _){
    if(_>0){
        numberOfWrong = _;
        return true;
    }
    return false;
}

/*
 *  Get Method
 *
 *  Ret:    The corresponding value
 */
char* Word::getEnglish() {
    return englist;
}

char* Word::getChinese() {
    return chinese;
}

const char* Word::getEnglish() const{
    return englist;
}

const char* Word::getChinese() const{
    return chinese;
}

Date Word::getDate() const{
    return date;
}

int Word::getNumberOfWrong() const{
    return numberOfWrong;
}

int Word::getNumberOfWatch() const{
    return numberOfWatch;
}

// ----- Judge method -----
/*
 *  Judge if the string is in english.
 *  It depend on the first two character.
 *
 *  Arg:    String
 *  Ret:    If it's english
 */
bool Word::isEnglish(const char* _){
    if(sizeof(_) < 2*sizeof(char)){
        cout << "The string is too short to judge" << endl;
        return false;
    }
    if( int(_[0])+int(_[0])>58 && 
        int(_[0])+int(_[0])<256 )
        return true;
    return false;
}

/*
 *  Judge if the string is in english.
 *  It's tricky that it would judge as chinese if it's not english
 *
 *  Arg:    String
 *  Ret:    If it's english'
 */
bool Word::isChinese(const char* _){
    return !(this->isEnglish(_));
}

// ----- Overloadding operator -----
Word& Word::operator=(const Word &wrd) {
	this->setEnglish(wrd.getEnglish());
    this->setChinese(wrd.getChinese());
    this->setDate(wrd.getDate());
    this->setNumberOfWrong(wrd.getNumberOfWrong());
    this->setNumberOfWatch(wrd.getNumberOfWatch());
	return *this;  					// Return a reference to myself.
}

bool Word::operator== (const int n) {
    if(n!=0){
        cout << "Error comparasion!" << endl;
        return false;
    }
    else{
        if( strcmp(englist, __enErr) == 0 &&
            strcmp(chinese, __chErr) == 0)
            return true;
        return false;
    }
}

bool Word::operator== (const Word &wrd){
    return ( !strcmp(englist, wrd.getEnglish()) &&
             !strcmp(chinese, wrd.getChinese()));
}