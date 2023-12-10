object Day7 {

    /*
    A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
     */

    // TODO Implement sorting for practice
    data class Card(val value: Char) : Comparable<Card> {

        private val order = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()

        init {
            require(order.contains(value)) {
                "invalid card $value"
            }
        }

        override fun compareTo(other: Card): Int {
            return this.order.indexOf(this.value).compareTo(this.order.indexOf(other.value))
        }
    }

    fun String.toHand(): List<Card> {
        return this.map { Card(it) }.toList()
    }

    data class Hand(val values: List<Card>) {
        init {
            require(values.count() == 5) { "Hand should always contain 5 cards" }
        }

        override fun toString(): String {
            return "Hand=${values.map { it.value }.joinToString("")}"
        }

        fun tieBreaker(other: Hand): Int {
            for (i in this.values.indices) {
                if (this.values[i].compareTo(other.values[i]) != 0) {
                    return this.values[i].compareTo(other.values[i])
                }
            }
            return 0
        }

        /*
      Five of a kind, where all five cards have the same label: AAAAA
       */
        fun isFiveOfKind(): Boolean {
            val firstChar = this.values[0]
            return this.values.all { it == firstChar }
        }

        /*
        Four of a kind, where four cards have the same label and one card has a different label: AA8AA
         */
        fun isFourOfKind(): Boolean {
            val pairings = this.values.groupBy { it.value }
            return pairings.maxOf { it.value.count() } == 4
        }

        /*
        Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
         */
        fun isFullHouse(): Boolean {
            val pairing = this.values.groupBy { it.value }.values.map { it.size }
            val count2 = pairing.count { it == 2 }
            val count3 = pairing.count { it == 3 }
            return count2 == 1 && count3 == 1
        }

        /*
        Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
         */
        fun isThreeOfKind(): Boolean {
            val pairing = this.values.groupBy { it.value }.values.map { it.size }
            val count2 = pairing.count { it == 2 }
            val count3 = pairing.count { it == 3 }
            return count2 == 0 && count3 == 1
        }

        /*
        Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
         */
        fun isTwoPair(): Boolean {
            val pairing = this.values.groupBy { it.value }.values.map { it.size }
            val count2 = pairing.count { it == 2 }
            return count2 == 2
        }

        /*
        One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
         */
        fun isOnePair(): Boolean {
            val pairing = this.values.groupBy { it.value }.values.map { it.size }
            val count2 = pairing.count { it == 2 }
            return count2 == 1 && pairing.maxOf { it } == 2
        }

        /*
         The rest is High card, where all cards' labels are distinct: 23456
         */
    }

    data class Play(val hand: Hand, val bid: Int) : Comparable<Play> {
        override fun compareTo(other: Play): Int {
            return when {
                hand.isFiveOfKind() -> {
                    when {
                        other.hand.isFiveOfKind() -> hand.tieBreaker(other.hand)
                        else -> 1
                    }
                }

                hand.isFourOfKind() -> {
                    when {
                        other.hand.isFiveOfKind() -> -1
                        other.hand.isFourOfKind() -> hand.tieBreaker(other.hand)
                        else -> 1
                    }
                }

                hand.isFullHouse() -> {
                    when {
                        other.hand.isFiveOfKind() || other.hand.isFourOfKind() -> -1
                        other.hand.isFullHouse() -> hand.tieBreaker(other.hand)
                        else -> 1
                    }
                }

                hand.isThreeOfKind() -> {
                    when {
                        other.hand.isFiveOfKind() || other.hand.isFourOfKind() || other.hand.isFullHouse() -> -1
                        other.hand.isThreeOfKind() -> hand.tieBreaker(other.hand)
                        else -> 1
                    }
                }

                hand.isTwoPair() -> {
                    when {
                        other.hand.isFiveOfKind() || other.hand.isFourOfKind() || other.hand.isFullHouse() || other.hand.isThreeOfKind() -> -1
                        other.hand.isTwoPair() -> hand.tieBreaker(other.hand)
                        else -> 1
                    }
                }

                hand.isOnePair() -> {
                    when {
                        other.hand.isFiveOfKind() || other.hand.isFourOfKind() || other.hand.isFullHouse() || other.hand.isThreeOfKind() || other.hand.isTwoPair() -> -1
                        other.hand.isOnePair() -> hand.tieBreaker(other.hand)
                        else -> 1
                    }
                }

                else -> {
                    when {
                        other.hand.isFiveOfKind() || other.hand.isFourOfKind() || other.hand.isFullHouse() || other.hand.isThreeOfKind() || other.hand.isTwoPair() || other.hand.isOnePair() -> -1
                        else -> hand.tieBreaker(other.hand)
                    }
                }
            }
        }


    }


    fun part1(): Long {
        val input = readFile("day7.txt")
        val plays = input.map { line ->
            val values = line.split(" ")
            Play(hand = Hand(values[0].map { Card(it) }.toList()), bid = values[1].toInt())
        }

        val result = plays.sorted().foldIndexed(0L) { i, acc, play ->
            acc + (i + 1) * play.bid
        }
        println("answer is $result")
        return result
    }

    fun part2(): Long {
        val input = readFile("day7.txt")

        val plays = input.map { line ->
            val values = line.split(" ")
            Play(hand = Hand(values[0].map { Card(it) }.toList()), bid = values[1].toInt())
        }

        val result = plays.sorted().foldIndexed(0L) { i, acc, play ->
            acc + (i + 1) * play.bid
        }
        println("answer is $result")
        return result
    }
}