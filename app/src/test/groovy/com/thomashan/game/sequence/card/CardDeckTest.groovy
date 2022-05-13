package com.thomashan.game.sequence.card

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.util.function.Predicate
import java.util.stream.Collectors
import java.util.stream.IntStream

import static com.thomashan.game.sequence.card.Card.of
import static com.thomashan.game.sequence.card.CardNumber.THREE
import static com.thomashan.game.sequence.card.CardNumber.TWO
import static com.thomashan.game.sequence.card.Suite.CLUB

class CardDeckTest {
    private CardDeck cardDeck

    @BeforeEach
    void setUp() {
        this.cardDeck = CardDeck.standardDeck()
    }

    @Test
    void testMinusFirstCard() {
        CardDeck newCardDeck = new CardDeck([of(TWO, CLUB), of(THREE, CLUB)]) - of(TWO, CLUB)

        assert 1 == newCardDeck.cards().size()
        assert of(THREE, CLUB) == newCardDeck.cards()[0]
    }

    @Test
    void testMinusSecondCard() {
        CardDeck newCardDeck = new CardDeck([of(TWO, CLUB), of(THREE, CLUB)]) - of(THREE, CLUB)

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
            .collect(Collectors.toList()) * 2
        assert predicate.test(orderedCards)
    }

    @Test
    void testDraw() {
        def (Card card, CardDeck deck) = cardDeck.draw()
        assert 1 == deck.cards().findAll { it == card }.size()
    }
}
