package com.thomashan.game.sequence.app

import com.thomashan.game.sequence.command.card.DealSetCards
import com.thomashan.game.sequence.command.game.StartGame
import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.card.CardNumber
import com.thomashan.game.sequence.domain.card.Suite
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.game.StartGameEvent

import static com.thomashan.game.sequence.domain.game.GameType.STANDARD

class App {
    static void main(String[] args) {
        StartGameEvent startGameEvent = new StartGame(2, STANDARD).execute()

        startGameEvent.game.ifPresent(game -> {
            Game inPlay = game
            println(inPlay)
            Map<Player, List<Card>> dealCards = Map.of(game.players()[0], [Card.of(CardNumber.TEN, Suite.SPADE)])
            inPlay = inPlay + new DealSetCards(game.cardDeck(), dealCards).execute()
            println(inPlay)
        })
    }
}
