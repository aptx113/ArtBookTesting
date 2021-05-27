package dependencyLibs

import dependencyVersions.TestDependencyVersions.ARCH_VERSION
import dependencyVersions.TestDependencyVersions.JUNIT_VERSION
import dependencyVersions.TestDependencyVersions.MOCKK_VERSION
import dependencyVersions.TestDependencyVersions.ROBOLECTRIC_VERSION
import dependencyVersions.TestDependencyVersions.TRUTH_VERSION

/**
 * All the Project Test dependencies are declared here.
 * These can be used across the Project
 */
object Arch {
    const val CORE = "androidx.arch.core:core-testing:${ARCH_VERSION}"
}

object Junit {
    const val JUNIT = "junit:junit:${JUNIT_VERSION}"
}

object Mockk {
    const val MOCKK = "io.mockk:mockk:${MOCKK_VERSION}"
}

object Robolectric {
    const val ROBO = "org.robolectric:robolectric:${ROBOLECTRIC_VERSION}"
}

object Truth {
    const val TRUTH = "com.google.truth:truth:${TRUTH_VERSION}"
}

object TestLibraries {
    val testLibraries = arrayListOf<String>().apply {
        add(Arch.CORE)
        add(Coroutines.TESTING)
        add(Hilt.TESTING)
        add(Junit.JUNIT)
        add(Mockk.MOCKK)
        add(Robolectric.ROBO)
        add(Room.TESTING)
        add(Truth.TRUTH)
    }
}
