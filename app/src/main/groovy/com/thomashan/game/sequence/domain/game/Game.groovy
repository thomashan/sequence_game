package com.thomashan.game.sequence.domain.game

import com.thomashan.game.sequence.GeneratedId
import com.thomashan.game.sequence.domain.board.Board
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.player.Players
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DealCardsFailed
import com.thomashan.game.sequence.events.card.DealtCards
import com.thomashan.game.sequence.events.card.DiscardCardEvent
import com.thomashan.game.sequence.events.card.DiscardCardFailed
import com.thomashan.game.sequence.events.card.DiscardedCard
import com.thomashan.game.sequence.events.card.DrawCardEvent
import com.thomashan.game.sequence.events.card.DrawCardFailed
import com.thomashan.game.sequence.events.card.DrewCard
import groovy.transform.ImmutableOptions
import groovy.transform.TypeCheckingMode

import static com.thomashan.game.sequence.domain.game.GameState.WAITING_FOR_PLAYER_TO_DISCARD
import static com.thomashan.game.sequence.domain.game.GameState.WAITING_FOR_PLAYER_TO_DRAW_OR_NEXT_PLAYER_TO_DISCARD

@ImmutableOptions(knownImmutables = ["players", "cardDeck", "board"])
record Game(GameType gameType, GameState gameState, int turnNumber, Players players, CardDeck cardDeck, Board board) implements GeneratedId {
    @groovy.transform.TypeChecked(TypeCheckingMode.SKIP)
    Game plus(DealCardsEvent dealCardsEvent) {
        return switch (dealCardsEvent) {
            case DealtCards -> {
                Players newPlayers = players + dealCardsEvent
                CardDeck newCardDeck = cardDeck + dealCardsEvent
                new Game(gameType, WAITING_FOR_PLAYER_TO_DISCARD, turnNumber, newPlayers, newCardDeck, board)
            }
            case DealCardsFailed -> this
            default -> throw new RuntimeException("Unknown event ${dealCardsEvent}")
        }
    }

    @groovy.transform.TypeChecked(TypeCheckingMode.SKIP)
    Game plus(DiscardCardEvent discardCardEvent) {
        return switch (discardCardEvent) {
            case DiscardedCard -> {
                Players newPlayers = players + discardCardEvent
                Board newBoard = board + discardCardEvent
                GameState gameState = discardCardEvent.player().allowAnyDiscard() ? WAITING_FOR_PLAYER_TO_DISCARD : WAITING_FOR_PLAYER_TO_DRAW_OR_NEXT_PLAYER_TO_DISCARD
                new Game(gameType, gameState, turnNumber + 1, newPlayers, cardDeck, newBoard)
            }
            case DiscardCardFailed -> this
            default -> throw new RuntimeException("Unknown event ${discardCardEvent}")
        }
    }

    @groovy.transform.TypeChecked(TypeCheckingMode.SKIP)
    Game plus(DrawCardEvent drawCardEvent) {
        return switch (drawCardEvent) {
            case DrewCard -> {
                Players newPlayers = players + drawCardEvent
                CardDeck newDeck = cardDeck + drawCardEvent
                new Game(gameType, WAITING_FOR_PLAYER_TO_DISCARD, turnNumber, newPlayers, newDeck, board)
            }
            case DrawCardFailed -> this
            default -> throw new RuntimeException("Unknown event ${drawCardEvent}")
        }
    }
}
