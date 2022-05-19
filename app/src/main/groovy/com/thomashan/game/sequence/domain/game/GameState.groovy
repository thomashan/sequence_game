package com.thomashan.game.sequence.domain.game

enum GameState {
    WAITING_FOR_CARDS_TO_BE_DEALT,
    WAITING_FOR_PLAYER_TO_DISCARD,
    WAITING_FOR_PLAYER_TO_DRAW_OR_NEXT_PLAYER_TO_DISCARD,
    COMPLETE
}
