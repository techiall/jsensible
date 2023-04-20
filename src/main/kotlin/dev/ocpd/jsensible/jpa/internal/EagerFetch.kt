package dev.ocpd.jsensible.jpa.internal

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.base.DescribedPredicate.describe
import com.tngtech.archunit.core.domain.JavaAnnotation
import com.tngtech.archunit.core.domain.JavaEnumConstant
import com.tngtech.archunit.core.domain.JavaMember
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.conditions.ArchConditions.beAnnotatedWith

/**
 * Eager fetch usage condition.
 */
object EagerFetch {

    /**
     * Matches eager fetch usage.
     */
    fun useEagerFetch(): ArchCondition<in JavaMember> =
        beAnnotatedWith(
            associationWithEagerFetch()
        ).`as`("use eager fetch")

    private fun associationWithEagerFetch(): DescribedPredicate<JavaAnnotation<*>> {
        val associationAnnotations = associationAnnotations()
        return describe("eager fetch annotation") { annotation: JavaAnnotation<*> ->
            if (annotation.type.name !in associationAnnotations) return@describe false

            val value = annotation["fetch"].orElse(null)
            value is JavaEnumConstant && value.name() == "EAGER"
        }
    }

    private fun associationAnnotations() = setOf(
        "javax.persistence.OneToMany",
        "javax.persistence.ManyToOne",
        "javax.persistence.OneToOne",
        "javax.persistence.ManyToMany",
        "javax.persistence.ElementCollection",
        "jakarta.persistence.OneToMany",
        "jakarta.persistence.ManyToOne",
        "jakarta.persistence.OneToOne",
        "jakarta.persistence.ManyToMany",
        "jakarta.persistence.ElementCollection",
        "org.hibernate.annotations.Any",
        "org.hibernate.annotations.ManyToAny"
    )
}
