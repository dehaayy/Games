-- INPUT FOLDER --
1st line represents the dimensions of the square Crossword puzzle

then the Puzzle is inputted as each row being a new line, and each row as a joined string:
an example of a 5x5 matrix:

azbaa
aabaa
aabca
dddac
efghi

after the matrix, input the number of words to search within the matrix
later each new line is the words to be searched within the puzzle

!DIAGONAL AND CIRCULAR WORDS ARE ALSO ACCEPTED!

-- OUTPUT --
Outputs the form : {r,c,d}
where
r: word's start row
c: word's start column
d: word's moving direction

If the word is not found the output would be {-1,-1,-1}

*for detailed directions check the directions.png
