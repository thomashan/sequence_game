package com.thomashan.game.sequence.domain.card


import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.util.function.Predicate
import java.util.stream.IntStream

import static com.thomashan.game.sequence.domain.card.Card.of
import static com.thomashan.game.sequence.domain.card.CardNumber.THREE
import static com.thomashan.game.sequence.domain.card.CardNumber.TWO
import static com.thomashan.game.sequence.domain.card.Suite.CLUB

class CardDeckTest {
    private CardDeck cardDeck

    @BeforeEach
    void setUp() {
        this.cardDeck = CardDeck.standardDeck()
    }

    @Test
    void testMinus_FirstCard() {
        CardDeck newCardDeck = new CardDeck([of(TWO, CLUB), of(THREE, CLUB)], false) - of(TWO, CLUB)

        assert 1 == newCardDeck.cards().size()
        assert of(THREE, CLUB) == newCardDeck.cards()[0]
    }

    @Test
    void testMinus_SecondCard() {
        CardDeck newCardDeck = new CardDeck([of(TWO, CLUB), of(THREE, CLUB)], false) - of(THREE, CLUB)

        assert 1 == newCardDeck.cards().size()
        assert of(TWO, CLUB) == newCardDeck.cards()[0]
    }

    @Test
    void testMinus_OnlyRemovesFirstInstance() {
        CardDeck newCardDeck = new CardDeck([of(TWO, CLUB), of(TWO, CLUB)], false) - of(TWO, CLUB)

        assert 1 == newCardDeck.cards().size()
        assert of(TWO, CLUB) == newCardDeck.cards()[0]
    }

    @Test
    void testStandardDeck_ReturnsTwoStandardDecks() {
        assert 52 * 2 == cardDeck.cards().size()
        for (Suite suite : Suite.values()) {
            for (CardNumber cardNumber : CardNumber.values()) {
                List<Card> cards = cardDeck.cards().findAll { it == of(cardNumber, suite) }
                assert 2 == cards.size()
            }
        }
    }

    @Test
    void testStandardDeck_IsShuffled() {
        Predicate<List<Card>> predicate = IntStream.range(0, cardDeck.cards().size())
            .mapToObj { Integer i ->
                { List<Card> cards ->
                    cards[i] != cardDeck[i]
                }
            }
            .reduce { Predicate<Card> cardPredicate1, Predicate<Card> cardPredicate2 ->
                cardPredicate1 | cardPredicate2
            }.get()
        List<Card> orderedCards = Arrays.stream(Suite.values())
            .flatMap { Suite suite ->
                Arrays.stream(CardNumber.values()).map(number -> of(number, suite))
            }
            .toList() * 2
        assert predicate.test(orderedCards)
    }
}
