#include <iostream>
#include <ctime>
#define YEAR_MAX 2016
#define MONTH_MAX 12
#define DAY_MAX 31
#define HOUR_MAX 24
#define MINUTE_MAX 60

class Date{
	public:
		// Insert method
		bool setYear(int _);
		bool setMonth(int _);
		bool setDay(int _);
		bool setHour(int _);
		bool setMinute(int _);

		// Get method
		int getYear() const;
		int getMonth() const;
		int getDay() const;
		int getHour() const;
		int getMinute() const;

		// Other method
		void Now();
		void show(struct tm* now);

		// Overloadding operator
		Date& operator=(const Date &rhs);
	private:
		int year;
		int month;
		int day;
		int hour;
		int minute;
};

void cc();
