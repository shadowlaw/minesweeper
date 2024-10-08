package com.shadowlaw.minesweeper.logic.board;


import com.shadowlaw.minesweeper.logic.constants.AdjacentSquareLocation;
import com.shadowlaw.minesweeper.logic.exceptions.SquareLocationNotFound;
import com.shadowlaw.minesweeper.logic.exceptions.SquareNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class GameGrid {

    private final Logger logger = LogManager.getLogger(GameGrid.class);
    private final int mineNumber;

    private final Square[][] gameGrid;
    private List<Position> minePositions;
    private int flagCount;
    private final List<Square> flaggedSquares = new ArrayList<>();
    private HashSet<Square> squaresWithAdjacentMines = new HashSet<>();
    private HashSet<Square> numberOfAdjacentMineSquaresOpened = new HashSet<>();

    public GameGrid(int rows, int columns, int mineNumber, int flagCount) {
        logger.debug("Initializing game grid with size {} X {}", rows, columns);
        this.gameGrid = new Square[rows][columns];
        this.mineNumber = mineNumber;
        this.flagCount = flagCount;
        logger.info("Game grid initialized");
    }

    public void addSquare(Square square) {
        logger.debug("adding square at cell {}:{} -> {}", square.getPosition().getRow(), square.getPosition().getColumn(), square.getFlatGridLocation());
        gameGrid[square.getPosition().getRow()][square.getPosition().getColumn()] = square;
        logger.debug("square added at cell {}:{} -> {}", square.getPosition().getRow(), square.getPosition().getColumn(), square.getFlatGridLocation());
    }

    public Square[][] getGameGrid() {
        return gameGrid;
    }

    public Square getSquare(int row, int column){
        try {
            return gameGrid[row][column];
        } catch (Exception e){
            logger.error("{}: {}", e.getClass(), e.getMessage());
            logger.trace(e.getStackTrace());
            throw new SquareNotFound(String.format("Unable to locate square with location %s:%s", row, column));
        }
    }

    public int getFlagCount() {
        return flagCount;
    }

    public int getSquaresWithAdjacentMines() {
        return squaresWithAdjacentMines.size();
    }

    public int getNumberOfAdjacentMineSquaresOpened() {
        return numberOfAdjacentMineSquaresOpened.size();
    }

    public int getRowFromGridCellNumber(int cellNumber) {
        logger.debug("calculating game square row for cell number {} and grid size {}", cellNumber, gameGrid.length);
        int calculatedRow = (int) Math.floor((double) cellNumber / gameGrid.length);
        logger.debug("row calculated at {}", calculatedRow);

        return calculatedRow;
    }

    public int getColumnFromGridCellNumber(int cellNumber) {
        logger.debug("calculating game square column for cell number {} and grid size {}", cellNumber, gameGrid.length);
        int calculatedColumn = cellNumber % gameGrid.length;
        logger.debug("column calculated at {}", calculatedColumn);

        return calculatedColumn;
    }

    public void initialize(int startingPosRow, int startingPosColumn){

        ArrayList<Square> safeSquares = new ArrayList<Square>(){{
            add(getSquare(startingPosRow, startingPosColumn));
        }};

        List<Position> minePositions = generateMineLocations(safeSquares);
        this.minePositions = minePositions;

        for(Position minePosition: minePositions){
            Square mineSquare = getSquare(minePosition.getRow(), minePosition.getColumn());
            mineSquare.setMine(true);

            if (mineSquare.getAdjacentMineNumber() > 0) {
                mineSquare.setAdjacentMineNumber(0);
                squaresWithAdjacentMines.remove(mineSquare);
            }

            List<Square> adjacentSquares = getAdjacentSquares(minePosition.getRow(), minePosition.getColumn());
            for(Square adjacentSquare: adjacentSquares) {
                if (adjacentSquare.isMine())
                    continue;

                adjacentSquare.setAdjacentMineNumber(
                        adjacentSquare.getAdjacentMineNumber() + 1
                );

                squaresWithAdjacentMines.add(adjacentSquare);

            }
        }
    }

    public List<Square> getAdjacentSquares(int row, int column) {
        List<Square> adjacentSquares = new ArrayList<>();

        for(AdjacentSquareLocation squareLocation: AdjacentSquareLocation.values()){
            try {
                adjacentSquares.add(getAdjacentSquare(squareLocation, row, column));
            } catch (SquareLocationNotFound e) {
                logger.warn(e.getMessage());
            }
        }

        return adjacentSquares;
    }

    public Square getAdjacentSquare(AdjacentSquareLocation squareLocation, int row, int column){

        logger.debug("Retrieving adjacent square for location {} of position {},{}", squareLocation.name(),  row, column);
        int rowOfInterest = squareLocation.getRowOfInterest(row);
        int columnOfInterest = squareLocation.getColumnOfInterest(column);

        logger.debug("rowOfInterest: {} | columnOfInterest: {}", rowOfInterest, columnOfInterest);

        if (rowOfInterest < 0 || rowOfInterest > gameGrid.length - 1 || columnOfInterest < 0 || columnOfInterest > gameGrid.length - 1) {
            throw new SquareLocationNotFound(String.format("Adjacent square location %s in position %s,%s not found", squareLocation, rowOfInterest, columnOfInterest));
        }

        Square square = getSquare(rowOfInterest, columnOfInterest);
        logger.debug("Adjacent square retrieved");
        return square;
    }

    public List<Position> generateMineLocations(ArrayList<Square> safeSquares) {
        List<Position> positions = new ArrayList<>();
        Random rand = new Random();
        logger.info("Generating mine positions");

        while (positions.size() < mineNumber) {

            boolean indexSeen = false;
            int randIndex = rand.nextInt(gameGrid.length*gameGrid.length );

            logger.debug("random index {} generated", randIndex);

            for(Square safeSquare : safeSquares){
                if (randIndex == safeSquare.getFlatGridLocation()) {
                    logger.debug("index {} already exists", randIndex);
                    indexSeen = true;
                    break;
                }
            }

            if (indexSeen) {
                continue;
            }

            safeSquares.add(new Square(0,0, randIndex));
            Position position = new Position(getRowFromGridCellNumber(randIndex), getColumnFromGridCellNumber(randIndex));
            positions.add(position);

            logger.debug("mine position {},{} accepted", position.getRow(), position.getColumn());
        }

        return positions;
    }

    public void openSquare(int row, int column) {
        Square square = getSquare(row, column);
        if(square.isOpened()) {
            logger.warn("square {}:{} already opened", row, column);
            return;
        }

        if (square.isFlagged()) {
            logger.warn("square {}:{} is flagged", row, column);
            return;
        }

        square.open(true);

        if (square.getAdjacentMineNumber() > 0 )
            numberOfAdjacentMineSquaresOpened.add(square);

        if (square.isMine()) {
            openMines();
        } else if (square.getAdjacentMineNumber() == 0) {
            openAllAdjacentEmptySquares(square.getPosition().getRow(), square.getPosition().getColumn());
        }
    }

    private void openAllAdjacentEmptySquares(int triggeredRow, int triggeredColumn) {

        logger.debug("opening adjacent squares to {}:{}", triggeredRow, triggeredColumn);

        LinkedHashSet<Square> adjacentSquaresSet = new LinkedHashSet<>(getAdjacentSquares(triggeredRow, triggeredColumn));
        List<Square> adjacentSquareList = Arrays.asList(adjacentSquaresSet.toArray(new Square[]{}));

        for (int squareIndex = 0; squareIndex < adjacentSquareList.size(); squareIndex++) {
            Square adjacentSquare = adjacentSquareList.get(squareIndex);
            if (adjacentSquare.getAdjacentMineNumber() == 0) {
                adjacentSquaresSet.addAll(getAdjacentSquares(adjacentSquare.getUiSquare().getRow(), adjacentSquare.getPosition().getColumn()));
                adjacentSquareList = Arrays.asList(adjacentSquaresSet.toArray(new Square[]{}));
            } else {
                numberOfAdjacentMineSquaresOpened.add(adjacentSquare);
            }
            if (adjacentSquare.isFlagged()) {
                toggleSquareFlag(adjacentSquare.getPosition().getRow(), adjacentSquare.getPosition().getColumn());
            }
            adjacentSquare.open();
        }
    }

    private void openMines() {
        for(Position minePosition: minePositions){
            Square mineSquare = getSquare(minePosition.getRow(), minePosition.getColumn());
            logger.debug("opening mine at position {}:{}", minePosition.getRow(), minePosition.getColumn());
            if(mineSquare.isFlagged()){
                logger.info("mine square is flagged. unable to open mine");
                continue;
            }
            mineSquare.open();
        }
    }

    public void toggleSquareFlag(int row, int column) {
        Square square = getSquare(row, column);

        if (square.isOpened()) {
            logger.warn("Unable to toggle flag. Square is opened");
            return;
        }

        if (square.isFlagged()) {
            flaggedSquares.remove(square);
        } else {
            if (flaggedSquares.size() >= flagCount){
                logger.warn("Unable to toggle flag. Game flag limit reached");
                return;
            }
            flaggedSquares.add(square);
        }

        square.setFlagged(!square.isFlagged());
        logger.info("square {}:{} flag toggled", row, column);
    }

    public void clearFlaggedSquares(){
        flaggedSquares.clear();
    }

    public int getAvailableFlags() {
        return flagCount - flaggedSquares.size();
    }

    public void flagAllMineSquares() {
        minePositions.forEach(minePositions ->
                getSquare(minePositions.getRow(), minePositions.getColumn()).setFlagged(true));
    }

    public void resetGameBoard() {
        for (int rowIndex = 0; rowIndex < gameGrid.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gameGrid[rowIndex].length; columnIndex++) {
                Square square = getSquare(rowIndex, columnIndex);

                square.setFlagged(false);
                square.setMine(false);
                square.setAdjacentMineNumber(0);
                square.close();
            }
        }
    }
}
