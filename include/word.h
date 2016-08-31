#ifndef _WORD_H
#define _WORD_H

#include <iostream>
#include "date.h"

#define ENGLISH_LENGTH 200
#define CHINESE_LENGTH 200
#define TREEVIEW_WATCH 0
#define TREEVIEW_WRONG 1

class Word{
    public:
        // Constructor
        Word(){}
        Word(char* eng, char* chi);

        // Insert method
        void setEnglish(const char* eng);
        void setChinese(const char* chi);
        void setDate(Date d);
        bool setNumberOfWrong(int _);
        bool setNumberOfWatch(int _);

        // Get method
        char* getEnglish();
        char* getChinese();
        const char* getEnglish()const;
        const char* getChinese()const;
        Date getDate() const;
        int getNumberOfWrong() const;
        int getNumberOfWatch() const;

        // Judge method
        bool isEnglish(const char* _);
        bool isChinese(const char* _);

        // Overloadding operator
        Word& operator=(const Word &wrd);
        bool operator== (const int n);
        bool operator== (const Word &wrd);

    private:
        char englist[ENGLISH_LENGTH];
        char chinese[CHINESE_LENGTH];
        Date date;
        int numberOfWrong;  // The times that choose wrong ansert
        int numberOfWatch;  // The times that watch the answer directly
};

#endif