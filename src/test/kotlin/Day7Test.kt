import Day7.Hand
import Day7.Play
import Day7.toHand
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertEquals

class Day7Test : FunSpec({

    data class TestData(val data: Hand, val answer: Boolean) : WithDataTestName {
        override fun dataTestName(): String {
            return "$data, $answer"
        }
    }

    data class TestPlays(val play1: Play, val play2: Play, val answer: Boolean) : WithDataTestName {
        override fun dataTestName(): String {
            return "play1: $play1, play2: $play2, $answer"
        }
    }

    context("Solutions") {
//        test("Part 1") {
//            val result = Day7.part1()
//            assertEquals(250474325, result)
//        }
        test("Part 2") {
            val result = Day7.part2()
            assertEquals(248909434, result)
        }
    }

    context("Poker hands validation") {
        context("Five of a kind") {
            withData(
                listOf(
                    TestData(Hand("AAAAA".toHand()), true),
                    TestData(Hand("QQQQQ".toHand()), true),
                    TestData(Hand("AAAAQ".toHand()), false),
                    TestData(Hand("AQQQQ".toHand()), false),
                )
            ) {
                it.data.isFiveOfKind() shouldBe it.answer
            }
        }

        context("Four of a kind") {
            withData(
                listOf(
                    TestData(Hand("AAAAQ".toHand()), true),
                    TestData(Hand("QAAAA".toHand()), true),
                    TestData(Hand("AAAAA".toHand()), false),
                    TestData(Hand("QQQQQ".toHand()), false),
                    TestData(Hand("AAQAA".toHand()), true),
                )
            ) {
                it.data.isFourOfKind() shouldBe it.answer
            }
        }

        context("Full house") {
            withData(
                listOf(
                    TestData(Hand("AAQQQ".toHand()), true),
                    TestData(Hand("QQQAA".toHand()), true),
                    TestData(Hand("QAQAQ".toHand()), true),
                    TestData(Hand("AAAAA".toHand()), false),
                    TestData(Hand("AAQQT".toHand()), false),
                )
            ) {
                it.data.isFullHouse() shouldBe it.answer
            }
        }

        context("Three of a kind") {
            withData(
                listOf(
                    TestData(Hand("AAAQT".toHand()), true),
                    TestData(Hand("QAAAT".toHand()), true),
                    TestData(Hand("QTAAA".toHand()), true),
                    TestData(Hand("AAAAA".toHand()), false),
                    TestData(Hand("AAAQQ".toHand()), false),
                    TestData(Hand("AQT34".toHand()), false),
                )
            ) {
                it.data.isThreeOfKind() shouldBe it.answer
            }
        }

        context("Two pair") {
            withData(
                listOf(
                    TestData(Hand("AAQQT".toHand()), true),
                    TestData(Hand("AATQQ".toHand()), true),
                    TestData(Hand("TAAQQ".toHand()), true),
                    TestData(Hand("AAAAA".toHand()), false),
                    TestData(Hand("AAAQQ".toHand()), false),
                    TestData(Hand("QQQQA".toHand()), false),
                )
            ) {
                it.data.isTwoPair() shouldBe it.answer
            }
        }

        context("One pair") {
            withData(
                listOf(
                    TestData(Hand("AAQT3".toHand()), true),
                    TestData(Hand("AQA34".toHand()), true),
                    TestData(Hand("AAQQ4".toHand()), false),
                    TestData(Hand("AAAAA".toHand()), false),
                    TestData(Hand("AAAQQ".toHand()), false),
                    TestData(Hand("AQT54".toHand()), false),
                )
            ) {
                it.data.isOnePair() shouldBe it.answer
            }
        }

    }

    context("Plays comparison") {
        context("Five of kind") {
            withData(
                listOf(
                    TestPlays(
                        play1 = Play(Hand("AAAAA".toHand()), 1), play2 = Play(Hand("44444".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("44444".toHand()), 1), play2 = Play(Hand("AAAAA".toHand()), 1), false
                    ),
                    TestPlays(
                        play1 = Play(Hand("QQQQQ".toHand()), 1), play2 = Play(Hand("JJJJJ".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("QQQQQ".toHand()), 1), play2 = Play(Hand("AQKT2".toHand()), 1), true
                    ),
                )
            ) {
                (it.play1 > it.play2) shouldBe it.answer
            }
        }

        context("Four of kind") {
            withData(
                listOf(
                    TestPlays(
                        play1 = Play(Hand("AAAAA".toHand()), 1), play2 = Play(Hand("AAAAQ".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAAAQ".toHand()), 1), play2 = Play(Hand("AAAAT".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("4AAAA".toHand()), 1), play2 = Play(Hand("33332".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAQQQ".toHand()), 1), play2 = Play(Hand("QQQQ2".toHand()), 1), false
                    ),
                )
            ) {
                (it.play1 > it.play2) shouldBe it.answer
            }
        }

        context("Full house") {
            withData(
                listOf(
                    TestPlays(
                        play1 = Play(Hand("AAAAA".toHand()), 1), play2 = Play(Hand("AAA22".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAAAQ".toHand()), 1), play2 = Play(Hand("AAA22".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA22".toHand()), 1), play2 = Play(Hand("QQQ22".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA22".toHand()), 1), play2 = Play(Hand("QQQQ2".toHand()), 1), false
                    ),
                )
            ) {
                (it.play1 > it.play2) shouldBe it.answer
            }
        }

        context("Three of kind") {
            withData(
                listOf(
                    TestPlays(
                        play1 = Play(Hand("AAAAA".toHand()), 1), play2 = Play(Hand("AAA23".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAAAQ".toHand()), 1), play2 = Play(Hand("AAA23".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA22".toHand()), 1), play2 = Play(Hand("QQQ23".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("6AAA4".toHand()), 1), play2 = Play(Hand("2QQQ3".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("3QQQ2".toHand()), 1), play2 = Play(Hand("QQ234".toHand()), 1), true
                    ),
                )
            ) {
                (it.play1 > it.play2) shouldBe it.answer
            }
        }

        context("Two pair") {
            withData(
                listOf(
                    TestPlays(
                        play1 = Play(Hand("AAAAA".toHand()), 1), play2 = Play(Hand("QAA22".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAAAQ".toHand()), 1), play2 = Play(Hand("QAA22".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA22".toHand()), 1), play2 = Play(Hand("QQ322".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA23".toHand()), 1), play2 = Play(Hand("QQAA2".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AA443".toHand()), 1), play2 = Play(Hand("QQ443".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AA443".toHand()), 1), play2 = Play(Hand("AA234".toHand()), 1), true
                    ),
                )
            ) {
                (it.play1 > it.play2) shouldBe it.answer
            }
        }

        context("One pair") {
            withData(
                listOf(
                    TestPlays(
                        play1 = Play(Hand("AAAAA".toHand()), 1), play2 = Play(Hand("QAA23".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAAAQ".toHand()), 1), play2 = Play(Hand("QAA23".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA22".toHand()), 1), play2 = Play(Hand("QAA23".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA23".toHand()), 1), play2 = Play(Hand("QAA23".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AA443".toHand()), 1), play2 = Play(Hand("QAA23".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("QAA24".toHand()), 1), play2 = Play(Hand("3AA24".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("QAA24".toHand()), 1), play2 = Play(Hand("3A924".toHand()), 1), true
                    ),
                )
            ) {
                (it.play1 > it.play2) shouldBe it.answer
            }
        }

        context("High card") {
            withData(
                listOf(
                    TestPlays(
                        play1 = Play(Hand("AAAAA".toHand()), 1), play2 = Play(Hand("3A924".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAAAQ".toHand()), 1), play2 = Play(Hand("3A924".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA22".toHand()), 1), play2 = Play(Hand("3A924".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AAA23".toHand()), 1), play2 = Play(Hand("3A924".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("AA443".toHand()), 1), play2 = Play(Hand("3A924".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("QAA23".toHand()), 1), play2 = Play(Hand("3A924".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("QAA23".toHand()), 1), play2 = Play(Hand("3A924".toHand()), 1), true
                    ),
                    TestPlays(
                        play1 = Play(Hand("QAJ24".toHand()), 1), play2 = Play(Hand("QAJ23".toHand()), 1), true
                    ),
                )
            ) {
                (it.play1 > it.play2) shouldBe it.answer
            }
        }


    }
})