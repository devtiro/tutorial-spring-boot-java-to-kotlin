package com.devtiro.database.domain.entities

import jakarta.persistence.*

@Entity
@Table(name = "authors")
class AuthorEntity {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    var id: Long? = null

    @JvmField
    var name: String? = null

    @JvmField
    var age: Int? = null

    constructor(id: Long?, name: String?, age: Int?) {
        this.id = id
        this.name = name
        this.age = age
    }

    constructor()

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is AuthorEntity) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$id`: Any? = this.id
        val `other$id`: Any? = other.id
        if (if (`this$id` == null) `other$id` != null else `this$id` != `other$id`) return false
        val `this$name`: Any? = this.name
        val `other$name`: Any? = other.name
        if (if (`this$name` == null) `other$name` != null else `this$name` != `other$name`) return false
        val `this$age`: Any? = this.age
        val `other$age`: Any? = other.age
        if (if (`this$age` == null) `other$age` != null else `this$age` != `other$age`) return false
        return true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is AuthorEntity
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$id`: Any? = this.id
        result = result * PRIME + (`$id`?.hashCode() ?: 43)
        val `$name`: Any? = this.name
        result = result * PRIME + (`$name`?.hashCode() ?: 43)
        val `$age`: Any? = this.age
        result = result * PRIME + (`$age`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "AuthorEntity(id=" + this.id + ", name=" + this.name + ", age=" + this.age + ")"
    }

    class AuthorEntityBuilder internal constructor() {
        private var id: Long? = null
        private var name: String? = null
        private var age: Int? = null
        fun id(id: Long?): AuthorEntityBuilder {
            this.id = id
            return this
        }

        fun name(name: String?): AuthorEntityBuilder {
            this.name = name
            return this
        }

        fun age(age: Int?): AuthorEntityBuilder {
            this.age = age
            return this
        }

        fun build(): AuthorEntity {
            return AuthorEntity(this.id, this.name, this.age)
        }

        override fun toString(): String {
            return "AuthorEntity.AuthorEntityBuilder(id=" + this.id + ", name=" + this.name + ", age=" + this.age + ")"
        }
    }

    companion object {
        @JvmStatic
        fun builder(): AuthorEntityBuilder {
            return AuthorEntityBuilder()
        }
    }
}
