#include <iostream>
#include <ctime>
#include "date.h"
using namespace std;

/*
	Insert Method

	Arg: the value want to set
	Ret: If successfully set
*/
bool Date::setYear(int _){
	if(_>YEAR_MAX)
		return false;
	year = _;
	return true;
}

bool Date::setMonth(int _){
	if(_>MONTH_MAX)
		return false;
	month = _;
	return true;
}

bool Date::setDay(int _){
	if(_>DAY_MAX)
		return false;
	day = _;
	return true;
}

bool Date::setHour(int _){
	if(_>HOUR_MAX)
		return false;
	hour = _;
	return true;
}

bool Date::setMinute(int _){
	if(_>MINUTE_MAX)
		return false;
	minute = _;
	return true;
}

/*
	Get Method

	Ret: The corresponding value
*/
int Date::getYear() const{
	return year;
}

int Date::getMonth() const{
	return month;
}

int Date::getDay() const{
	return day;
}

int Date::getHour() const{
	return hour;
}

int Date::getMinute() const{
	return minute;
}

// ----- Other method -----
// Get the current time
void Date::Now(){
	time_t d = time(0);
	struct tm *now = localtime(&d);
	//this->show(now);
	this->setYear(now->tm_year+1900);
	this->setMonth(now->tm_mon+1);
	this->setDay(now->tm_mday);
	this->setHour(now->tm_hour);
	this->setMinute(now->tm_min);
}

/*
	Show the time information

	Arg: The tm structure defined in time.h
*/
void Date::show(struct tm* now){
	cout << "year: " << (now->tm_year+1900) << endl
		 << "month: " << (now->tm_mon+1) << endl
		 << "day: " << now->tm_mday << endl 
		 << "hour: " << now->tm_hour << endl
		 << "minute: " << now->tm_min << endl;
}

// ----- Overloadding operator -----
Date& Date::operator=(const Date &rhs) {
	setYear(rhs.getYear());
	setMonth(rhs.getMonth());
	setDay(rhs.getDay());
	setHour(rhs.getHour());
	setMinute(rhs.getMinute());
	return *this;  					// Return a reference to myself.
}