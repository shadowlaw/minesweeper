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

    public GameGrid(int rows, int columns, int mineNumber) {
        logger.debug("Initializing game grid with size {} X {}", rows, columns);
        this.gameGrid = new Square[rows][columns];
        this.mineNumber = mineNumber;
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
            List<Square> adjacentSquares = getAdjacentSquares(minePosition.getRow(), minePosition.getColumn());
            for(Square adjacentSquare: adjacentSquares)
                adjacentSquare.setAdjacentMineNumber(
                        adjacentSquare.getAdjacentMineNumber() + 1
                );
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

        while (positions.size() < mineNumber) {
            logger.info("Generating mine positions");

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
            return;
        }

        square.open(true);

        if (square.isMine()) {
            openMines();
        }
    }

    private void openMines() {
        for(Position minePosition: minePositions){
            Square mineSquare = getSquare(minePosition.getRow(), minePosition.getColumn());
            logger.debug("opening mine at position {}:{}", minePosition.getRow(), minePosition.getColumn());
            mineSquare.open();
        }
    }
}
