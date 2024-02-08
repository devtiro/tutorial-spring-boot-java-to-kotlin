package com.devtiro.database.domain.dto;

public class BookDto {

    private String isbn;

    private String title;

    private AuthorDto author;

    public BookDto(String isbn, String title, AuthorDto author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public BookDto() {
    }

    public static BookDtoBuilder builder() {
        return new BookDtoBuilder();
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public AuthorDto getAuthor() {
        return this.author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BookDto)) return false;
        final BookDto other = (BookDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$isbn = this.getIsbn();
        final Object other$isbn = other.getIsbn();
        if (this$isbn == null ? other$isbn != null : !this$isbn.equals(other$isbn)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$author = this.getAuthor();
        final Object other$author = other.getAuthor();
        if (this$author == null ? other$author != null : !this$author.equals(other$author)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $isbn = this.getIsbn();
        result = result * PRIME + ($isbn == null ? 43 : $isbn.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $author = this.getAuthor();
        result = result * PRIME + ($author == null ? 43 : $author.hashCode());
        return result;
    }

    public String toString() {
        return "BookDto(isbn=" + this.getIsbn() + ", title=" + this.getTitle() + ", author=" + this.getAuthor() + ")";
    }

    public static class BookDtoBuilder {
        private String isbn;
        private String title;
        private AuthorDto author;

        BookDtoBuilder() {
        }

        public BookDtoBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookDtoBuilder author(AuthorDto author) {
            this.author = author;
            return this;
        }

        public BookDto build() {
            return new BookDto(this.isbn, this.title, this.author);
        }

        public String toString() {
            return "BookDto.BookDtoBuilder(isbn=" + this.isbn + ", title=" + this.title + ", author=" + this.author + ")";
        }
    }
}
