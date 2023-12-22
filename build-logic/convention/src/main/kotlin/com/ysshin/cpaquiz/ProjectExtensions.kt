package com.ysshin.cpaquiz

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.gitHash: String
    get() {
        val stdout = ByteArrayOutputStream()
        exec {
            commandLine("git", "rev-parse", "--short", "HEAD")
            standardOutput = stdout
        }
        return stdout.toString().trim()
    }

val Project.gitBranchOrTag: String
    get() {
        System.getenv("GIT_BRANCH")?.takeIf { it.isNotBlank() }?.let {
            return it.replace('/', '-')
        }

        val branchOutputStream = ByteArrayOutputStream()
        exec {
            commandLine("git", "symbolic-ref", "--short", "-q", "HEAD")
            standardOutput = branchOutputStream
            isIgnoreExitValue = true
        }

        val gitBranchOrTag = if (branchOutputStream.size() > 0) {
            branchOutputStream.toString().trim()
        } else {
            val tagOutputStream = ByteArrayOutputStream()
            exec {
                commandLine("git", "describe", "--tags", "--exact-match")
                standardOutput = tagOutputStream
                errorOutput = ByteArrayOutputStream()
                isIgnoreExitValue = true
            }
            tagOutputStream.toString().trim()
        }
        return gitBranchOrTag.replace('/', '-')
    }

val Project.projectArchivesName: String
    get() {
        val android = requireNotNull(project.extensions.findByType(AppExtension::class.java))
        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"))
        val outputName = buildString {
            append("CpaQuiz-v${android.defaultConfig.versionName}(${android.defaultConfig.versionCode})-$date-${project.gitBranchOrTag}(${project.gitHash})")
        }
        return outputName
    }