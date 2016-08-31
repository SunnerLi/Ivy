# The flag and compilier
CC=g++
CFLAGS=-Wall -Wwrite-strings -Wreturn-type

# The source and lib path
INCDIR=./include/
LIBDIR=./src/
OBJDIR=./obj/

# The obj file
OBJ=date.o word.o node.o tree.o err.o
OBJS=$(addprefix $(OBJDIR), $(OBJ))

# The target file
TARGET=main
MAIN=$(TARGET).cpp

# Herder path
CFLAGS+=-I $(INCDIR)

# Main makefile
all:$(TARGET)

$(TARGET) : $(OBJ) 
	$(CC) $(CFLAGS) $(OBJS) $(MAIN) -o $@

$(OBJ): %.o : $(LIBDIR)%.cpp
	$(CC) $(CFLAGS) -c $< -o $(OBJDIR)$@

clean:
	rm -r $(OBJS)
	rm -r $(TARGET)
