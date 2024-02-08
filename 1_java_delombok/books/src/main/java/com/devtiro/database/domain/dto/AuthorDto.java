package com.devtiro.database.domain.dto;

public class AuthorDto {

    private Long id;

    private String name;

    private Integer age;

    public AuthorDto(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public AuthorDto() {
    }

    public static AuthorDtoBuilder builder() {
        return new AuthorDtoBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AuthorDto)) return false;
        final AuthorDto other = (AuthorDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$age = this.getAge();
        final Object other$age = other.getAge();
        if (this$age == null ? other$age != null : !this$age.equals(other$age)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AuthorDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $age = this.getAge();
        result = result * PRIME + ($age == null ? 43 : $age.hashCode());
        return result;
    }

    public String toString() {
        return "AuthorDto(id=" + this.getId() + ", name=" + this.getName() + ", age=" + this.getAge() + ")";
    }

    public static class AuthorDtoBuilder {
        private Long id;
        private String name;
        private Integer age;

        AuthorDtoBuilder() {
        }

        public AuthorDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AuthorDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AuthorDtoBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public AuthorDto build() {
            return new AuthorDto(this.id, this.name, this.age);
        }

        public String toString() {
            return "AuthorDto.AuthorDtoBuilder(id=" + this.id + ", name=" + this.name + ", age=" + this.age + ")";
        }
    }
}
