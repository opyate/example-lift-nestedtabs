package sample.snippet

import xml.NodeSeq
import net.liftweb.util.BindHelpers._

class Tabs {
  private val data = List(
      Author("JK Rowling", List(
        Book("Harry Potter and the Deathly Hallows", List(Para("para1"), Para("para2"))),
        Book("Harry Potter and the Goblet of Fire", List(Para("para3"), Para("para4"))))),
      Author ("Joshua Suereth", List(Book("Scala in Depth", List(Para("para5"), Para("para6"))))))


  def render(xhtml: NodeSeq): NodeSeq = {

    data.flatMap(author => bind("a", xhtml,
      "name" -> author.name,
      "books" -> author.books.flatMap(book =>
        bind("b", chooseTemplate("book", "list", xhtml),
          "name" -> book.name,
          "paras" -> book.paras.flatMap(para =>
            bind("p", chooseTemplate("para", "list", xhtml),
              "text" -> para.text)
          )
        )
      )
    ))
  }

  def renderWithTabs(xhtml: NodeSeq): NodeSeq = {

    bind("root", xhtml, "authors" ->
      data.flatMap(author => bind("author", chooseTemplate("authors", "template", xhtml),
        "name" -> author.name,
        "books" -> author.books.flatMap(book =>
          bind("book", chooseTemplate("books", "template", xhtml),
            "name" -> book.name,
            "extract" -> book.paras.flatMap(para =>
              bind("extract", chooseTemplate("extracts", "template", xhtml),
                "text" -> para.text)
            )
          )
        )
      ))
    )
  }

}

case class Para(text: String)
case class Book(name: String, paras: List[Para])
case class Author(name: String, books: List[Book])
