package com.thomashan.game.sequence.command.card


import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DrawCardEvent
import com.thomashan.game.sequence.events.card.DrawCardFailed
import com.thomashan.game.sequence.events.card.DrewCard
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["game", "player"])
record DrawFromTop(Game game, Player player) implements DrawCard {
    @Override
    DrawCardEvent execute() {
        Optional<DrawCardFailed> failureEvent = failureEvent
        if (!failureEvent.empty) {
            return failureEvent.get()
        }
        return new DrewCard(player, game.cardDeck().cards().head())
    }

    @Override
    Game getGame() {
        return game
    }

    @Override
    Player getPlayer() {
        return player
    }
}
