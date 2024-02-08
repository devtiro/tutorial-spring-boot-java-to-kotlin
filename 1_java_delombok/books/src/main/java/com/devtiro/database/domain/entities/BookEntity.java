package com.devtiro.database.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;

    public BookEntity(String isbn, String title, AuthorEntity authorEntity) {
        this.isbn = isbn;
        this.title = title;
        this.authorEntity = authorEntity;
    }

    public BookEntity() {
    }

    public static BookEntityBuilder builder() {
        return new BookEntityBuilder();
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public AuthorEntity getAuthorEntity() {
        return this.authorEntity;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorEntity(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BookEntity)) return false;
        final BookEntity other = (BookEntity) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$isbn = this.getIsbn();
        final Object other$isbn = other.getIsbn();
        if (this$isbn == null ? other$isbn != null : !this$isbn.equals(other$isbn)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$authorEntity = this.getAuthorEntity();
        final Object other$authorEntity = other.getAuthorEntity();
        if (this$authorEntity == null ? other$authorEntity != null : !this$authorEntity.equals(other$authorEntity))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookEntity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $isbn = this.getIsbn();
        result = result * PRIME + ($isbn == null ? 43 : $isbn.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $authorEntity = this.getAuthorEntity();
        result = result * PRIME + ($authorEntity == null ? 43 : $authorEntity.hashCode());
        return result;
    }

    public String toString() {
        return "BookEntity(isbn=" + this.getIsbn() + ", title=" + this.getTitle() + ", authorEntity=" + this.getAuthorEntity() + ")";
    }

    public static class BookEntityBuilder {
        private String isbn;
        private String title;
        private AuthorEntity authorEntity;

        BookEntityBuilder() {
        }

        public BookEntityBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookEntityBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookEntityBuilder authorEntity(AuthorEntity authorEntity) {
            this.authorEntity = authorEntity;
            return this;
        }

        public BookEntity build() {
            return new BookEntity(this.isbn, this.title, this.authorEntity);
        }

        public String toString() {
            return "BookEntity.BookEntityBuilder(isbn=" + this.isbn + ", title=" + this.title + ", authorEntity=" + this.authorEntity + ")";
        }
    }
}
