package minesweeper

import kotlin.random.Random

class Field(val minesNumber: Int, val row: Int = 9, val col: Int = 9) {
    val field =  Array(row) { Array(col) { "." } }
    val playerField = Array(row) { Array(col) { "." } }
    init {
        val rnd = Random
        val rndMines = Array(row * col) {"."}
        var mines = 0
        while (mines < minesNumber) {
            val i = rnd.nextInt(0, rndMines.lastIndex + 1)
            if (rndMines[i] != "X") {
                rndMines[i] = "X"
                mines ++
            }
        }
        for (r in field.indices) {
            for (c in field[r].indices) {
                field[r][c] = rndMines[r * row + c]
            }
        }
    }

    fun neighbours() {
        for (r in field.indices) {
            for (c in field[r].indices) {
                var neighbour = 0
                if (field[r][c] != "X") {
                    try {
                        if (field[r - 1][c - 1] == "X") {
                            neighbour++
                        }
                    } catch (_: Exception) {}
                    try {
                        if (field[r - 1][c] == "X") {
                            neighbour++
                        }
                    } catch (_: Exception) {}
                    try {
                        if (field[r - 1][c + 1] == "X") {
                            neighbour++
                        }
                    } catch (_: Exception) {}
                    try {
                        if (field[r][c + 1] == "X") {
                            neighbour++
                        }
                    } catch (_: Exception) {}
                    try {
                        if (field[r + 1][c + 1] == "X") {
                            neighbour++
                        }
                    } catch (_: Exception) {}
                    try {
                        if (field[r + 1][c] == "X") {
                            neighbour++
                        }
                    } catch (_: Exception) {}
                    try {
                        if (field[r + 1][c - 1] == "X") {
                            neighbour++
                        }
                    } catch (_: Exception) {}
                    try {
                        if (field[r][c - 1] == "X") {
                            neighbour++
                        }
                    } catch (_: Exception) {}
                }
                if (neighbour != 0) {
                    field[r][c] = neighbour.toString()
                }
            }
        }
    }

    fun print() {
        println(" │123456789│")
        println("—│—————————│")
        for (r in playerField.indices) {
            print("${r + 1}|")
            for (ch in playerField[r]) {
                if (ch == "X") print(".") else print(ch)
            }
            println("|")
        }
        println("—│—————————│")
    }

    fun game() {
        var gameOver = false
        var wrong = 0
        var mines = 0
        var notMines = 0
        this.print()
        while (!gameOver) {
            if ((mines == minesNumber || notMines == row * col - minesNumber) && wrong == 0) {
                println("Congratulations! You found all the mines!")
                gameOver = true
            } else {
                print("Set/unset mines marks or claim a cell as free: ")
                val (cStr, rStr, command) = readln().split(" ")
                val c = cStr.toInt() - 1
                val r = rStr.toInt() - 1
                if (command == "mine") {
                    if (playerField[r][c] == ".") {
                        if (field[r][c] == "X") {
                            playerField[r][c] = "*"
                            mines++
                        } else {
                            playerField[r][c] = "*"
                            wrong++
                        }
                    } else {
                        if (field[r][c] == "X") {
                            playerField[r][c] = "."
                            mines--
                        } else {
                            playerField[r][c] = "."
                            wrong--
                        }
                    }
                } else {
                    if (field[r][c] == "X") {
                        for (x in field.indices) {
                            for (y in field[x].indices) {
                                if (field[x][y] == "X") {
                                    playerField[x][y] = field[x][y]
                                }
                            }
                        }
                        this.print()
                        println("You stepped on a mine and failed!")
                        return
                    } else if (field[r][c] == ".") {
                        val q = mutableListOf(listOf(r, c))
                        while (q.isNotEmpty()) {
                            val (x, y) = q.removeAt(q.lastIndex)
                            if (playerField[x][y] == "." || playerField[x][y] == "*") {
                                if  (playerField[x][y] == "*") {
                                    wrong--
                                }
                                playerField[x][y] = "/"
                                notMines++
                            }
                            try {
                                if (playerField[x - 1][y - 1] == "." || playerField[x - 1][y - 1] == "*") {
                                    if (field[x - 1][y - 1] == ".") {
                                        q.add(listOf(x - 1, y - 1))
                                        if  (playerField[x - 1][y - 1] == "*") {
                                            wrong--
                                        }
                                        playerField[x - 1][y - 1] = "/"
                                        notMines++
                                    } else if (field[x - 1][y - 1] != "X") {
                                        playerField[x - 1][y - 1] = field[x - 1][y - 1]
                                        notMines++
                                    }
                                }
                            } catch (_: Exception) {}
                            try {
                                if (playerField[x - 1][y] == "." || playerField[x - 1][y] == "*") {
                                    if (field[x - 1][y] == ".") {
                                        q.add(listOf(x - 1, y))
                                        if  (playerField[x - 1][y] == "*") {
                                            wrong--
                                        }
                                        playerField[x - 1][y] = "/"
                                        notMines++
                                    } else if (field[x - 1][y] != "X") {
                                        playerField[x - 1][y] = field[x - 1][y]
                                        notMines++
                                    }
                                }
                            } catch (_: Exception) {}
                            try {
                                if (playerField[x - 1][y + 1] == "." || playerField[x - 1][y + 1] == "*") {
                                    if (field[x - 1][y + 1] == ".") {
                                        q.add(listOf(x - 1, y + 1))
                                        if  (playerField[x - 1][y + 1] == "*") {
                                            wrong--
                                        }
                                        playerField[x - 1][y + 1] = "/"
                                        notMines++
                                    } else if (field[x - 1][y + 1] != "X") {
                                        playerField[x - 1][y + 1] = field[x - 1][y + 1]
                                        notMines++
                                    }
                                }
                            } catch (_: Exception) {}
                            try {
                                if (playerField[x][y - 1] == "." || playerField[x][y - 1] == "*") {
                                    if (field[x][y - 1] == ".") {
                                        q.add(listOf(x, y - 1))
                                        if  (playerField[x][y - 1] == "*") {
                                            wrong--
                                        }
                                        playerField[x][y - 1] = "/"
                                        notMines++
                                    } else if (field[x][y - 1] != "X") {
                                        playerField[x][y - 1] = field[x][y - 1]
                                        notMines++
                                    }
                                }
                            } catch (_: Exception) {}
                            try {
                                if (playerField[x][y + 1] == "." || playerField[x][y + 1] == "*") {
                                    if (field[x][y + 1] == ".") {
                                        q.add(listOf(x, y + 1))
                                        if  (playerField[x][y + 1] == "*") {
                                            wrong--
                                        }
                                        playerField[x][y + 1] = "/"
                                        notMines++
                                    } else if (field[x][y + 1] != "X") {
                                        playerField[x][y + 1] = field[x][y + 1]
                                        notMines++
                                    }
                                }
                            } catch (_: Exception) {}
                            try {
                                if (playerField[x + 1][y - 1] == "." || playerField[x + 1][y - 1] == "*") {
                                    if (field[x + 1][y - 1] == ".") {
                                        q.add(listOf(x + 1, y - 1))
                                        if  (playerField[x + 1][y - 1] == "*") {
                                            wrong--
                                        }
                                        playerField[x + 1][y - 1] = "/"
                                        notMines++
                                    } else if (field[x + 1][y - 1] != "X") {
                                        playerField[x + 1][y - 1] = field[x + 1][y - 1]
                                        notMines++
                                    }
                                }
                            } catch (_: Exception) {}
                            try {
                                if (playerField[x + 1][y] == "." || playerField[x + 1][y] == "*") {
                                    if (field[x + 1][y] == ".") {
                                        q.add(listOf(x + 1, y))
                                        if  (playerField[x + 1][y] == "*") {
                                            wrong--
                                        }
                                        playerField[x + 1][y] = "/"
                                        notMines++
                                    } else if (field[x + 1][y] != "X") {
                                        playerField[x + 1][y] = field[x + 1][y]
                                        notMines++
                                    }
                                }
                            } catch (_: Exception) {}
                            try {
                                if (playerField[x + 1][y + 1] == "." || playerField[x + 1][y + 1] == "*") {
                                    if (field[x + 1][y + 1] == ".") {
                                        q.add(listOf(x + 1, y + 1))
                                        if  (playerField[x + 1][y + 1] == "*") {
                                            wrong--
                                        }
                                        playerField[x + 1][y + 1] = "/"
                                        notMines++
                                    } else if (field[x + 1][y + 1] != "X") {
                                        playerField[x + 1][y + 1] = field[x + 1][y + 1]
                                        notMines++
                                    }
                                }
                            } catch (_: Exception) {}
                        }
                    } else {
                        playerField[r][c] = field[r][c]
                        notMines++
                    }
                }
                this.print()
            }
        }
    }
}

fun main() {
    print("How many mines do you want on the field? ")
    val minesNumber = readln().toInt()
    val field = Field(minesNumber)
    field.neighbours()
    field.game()
}
