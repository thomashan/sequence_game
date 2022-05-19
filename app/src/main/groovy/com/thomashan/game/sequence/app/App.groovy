package com.thomashan.game.sequence.app


import com.thomashan.game.sequence.command.card.DealSetCards
import com.thomashan.game.sequence.command.card.DiscardCard
import com.thomashan.game.sequence.command.card.DrawFromTop
import com.thomashan.game.sequence.command.card.DrawSetCard
import com.thomashan.game.sequence.command.game.StartGame
import com.thomashan.game.sequence.domain.board.Position
import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Colour
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.domain.player.Players
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DiscardCardEvent
import com.thomashan.game.sequence.events.card.DrawCardEvent
import com.thomashan.game.sequence.events.game.StartGameEvent

import static com.thomashan.game.sequence.domain.card.CardNumber.*
import static com.thomashan.game.sequence.domain.card.Suite.*
import static com.thomashan.game.sequence.domain.game.GameType.STANDARD

class App {
    static void main(String[] args) {
        Players players = new Players([
            new Player("Player 1", Colour.RED, 0, false, []),
            new Player("Player 2", Colour.GREEN, 0, false, []),
        ])
        StartGameEvent startGameEvent = new StartGame(players, STANDARD).execute()

        println(startGameEvent)

        startGameEvent.game.ifPresent(game -> {
            Game inPlay = game
            println(inPlay)
            Map<Player, List<Card>> dealCards = Map.of(
                inPlay.players()[0], [Card.of(TEN, SPADE),
                                      Card.of(JACK, SPADE),
                                      Card.of(JACK, SPADE),
                                      Card.of(JACK, CLUB),
                                      Card.of(JACK, HEART),
                                      Card.of(JACK, DIAMOND),
                                      Card.of(JACK, DIAMOND)]
            )
            DealCardsEvent dealCardsEvent = new DealSetCards(inPlay, dealCards).execute()
            inPlay = inPlay + dealCardsEvent
            println(inPlay)

            DiscardCardEvent discardCardEvent = new DiscardCard(inPlay, inPlay.players()[0], Card.of(TEN, SPADE), Position.of(8, 4)).execute()
            inPlay = inPlay + discardCardEvent
            println(inPlay)
            DrawCardEvent drawCardEvent = new DrawSetCard(inPlay, inPlay.players()[0], Card.of(TEN, SPADE)).execute()
            inPlay = inPlay + drawCardEvent
            println(inPlay)

            discardCardEvent = new DiscardCard(inPlay, inPlay.players()[1], Card.of(SEVEN, SPADE), Position.of(8, 1)).execute()
            inPlay = inPlay + discardCardEvent
            println(inPlay)

            discardCardEvent = new DiscardCard(inPlay, inPlay.players()[0], Card.of(JACK, CLUB), Position.of(0, 8)).execute()
            inPlay = inPlay + discardCardEvent
            println(inPlay)
            drawCardEvent = new DrawFromTop(inPlay, inPlay.players()[0]).execute()
            inPlay = inPlay + drawCardEvent
            println(inPlay)

            discardCardEvent = new DiscardCard(inPlay, inPlay.players()[1], Card.of(THREE, CLUB), Position.of(4, 6)).execute()
            inPlay = inPlay + discardCardEvent
            println(inPlay)

            discardCardEvent = new DiscardCard(inPlay, inPlay.players()[0], Card.of(JACK, SPADE), Position.of(4, 6)).execute()
            inPlay = inPlay + discardCardEvent
            println(inPlay)
            drawCardEvent = new DrawFromTop(inPlay, inPlay.players()[0]).execute()
            inPlay = inPlay + drawCardEvent
            println(inPlay)
        })
    }
}
