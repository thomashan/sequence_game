package com.thomashan.game.sequence.domain.board

record Position(int column, int row) {
    static Position of(int column, int row) {
        return new Position(column, row)
    }
}
