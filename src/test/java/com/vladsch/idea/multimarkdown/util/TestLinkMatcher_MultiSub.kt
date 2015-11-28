/*
 * Copyright (c) 2015-2015 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package com.vladsch.idea.multimarkdown.util

import com.vladsch.idea.multimarkdown.TestUtils.*
import org.junit.Test
import java.util.*

class TestLinkMatcher_MultiSub {
    val projectResolver = MarkdownTestData

    @Test fun test_linkRefMatcher_SubDirMultiWiki() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md")
        val linkRef = LinkRef(linkInfo, linkInfo.fileNameNoExt, null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, true)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }
        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/SubDirectory/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.markdown",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.mkd"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDirMultiRepo() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/Multiple-Match.md")
        val linkRef = LinkRef(linkInfo, linkInfo.fileName, null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, true)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/SubDirectory/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.markdown",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.mkd",
                "/Users/vlad/src/MarkdownTest/SubDirectory/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/Multiple-Match.markdown",
                "/Users/vlad/src/MarkdownTest/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/Multiple-Match.mkd"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDirMulti_Readme() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/single-link-test.md")
        val linkRef = LinkRef(linkInfo, "Readme.md", null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, true)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/untitled/README.md",
                "/Users/vlad/src/MarkdownTest/Readme.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-datepicker/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-datepicker/tests/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/cropper/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/eonasdan-bootstrap-datetimepicker/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/autoNumeric/readme.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-modal/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/autoNumeric/autoNumeric-2.0/readme.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-datepicker/docs/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-daterangepicker/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-sass/README.md"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDirMulti_ReadmeNoExt() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/single-link-test.md")
        val linkRef = LinkRef(linkInfo, "Readme", null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, true)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/untitled/README.md",
                "/Users/vlad/src/MarkdownTest/Readme.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-datepicker/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-datepicker/tests/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/cropper/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/eonasdan-bootstrap-datetimepicker/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/autoNumeric/readme.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-modal/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/autoNumeric/autoNumeric-2.0/readme.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-datepicker/docs/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-daterangepicker/README.md",
                "/Users/vlad/src/MarkdownTest/GitHubIssues/Issue-46/webbeheer_package/bower_components/bootstrap-sass/README.md"
        ), list)
    }

    @Test fun test_WikiLinkRefMatcher_SubDirMulti() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md")
        val linkRef = LinkRef.parseLinkRef(linkInfo, linkInfo.fileName, null, ::WikiLinkRef)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, true)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/SubDirectory/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.markdown",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.mkd"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDir() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/SubDirectory/NestedFile.md")
        val linkRef = LinkRef(linkInfo, "SubDirectory/NestedFile.md", null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, true)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/SubDirectory/NestedFile.md"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDirMultiExact_Repo() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/Multiple-Match.mkd")
        val linkRef = LinkRef(linkInfo, linkInfo.fileName, null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, false)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/Multiple-Match.mkd"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDirMultiExact_Readme() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/single-link-test.md")
        val linkRef = LinkRef(linkInfo, "Readme.md", null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, false)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/Readme.md"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDirMultiExact_ReadmeNoExt() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/single-link-test.md")
        val linkRef = LinkRef(linkInfo, "Readme", null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, false)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
        ), list)
    }

    @Test fun test_WikiLinkRefMatcher_SubDirMultiExactNoExt() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md")
        val linkRef = LinkRef.parseLinkRef(linkInfo, linkInfo.fileNameNoExt, null, ::WikiLinkRef)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, false)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/SubDirectory/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.markdown",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md",
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.mkd"
        ), list)
    }

    @Test fun test_WikiLinkRefMatcher_SubDirMultiExactWithExt() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md")
        val linkRef = LinkRef.parseLinkRef(linkInfo, linkInfo.fileName, null, ::WikiLinkRef)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, false)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md"
        ), list)
    }

    @Test fun test_WikiLinkRefMatcher_SubDirMultiExact_Smart() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.md")
        val linkRef = LinkRef.parseLinkRef(linkInfo, linkInfo.fileNameNoExt, null, ::WikiLinkRef)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, false)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()
        var bestMatch = ""

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    if (bestMatch.isEmpty() || bestMatch > path) bestMatch = path;
                }
            }
        }

        list.add(bestMatch)

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/MarkdownTest.wiki/Multiple-Match.markdown"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDirExact() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/SubDirectory/NestedFile.md")
        val linkRef = LinkRef(linkInfo, "SubDirectory/NestedFile.md", null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, false)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText()
        val regex = linkRefMatcher.patternRegex()

        if (regex != null) {
            for (path in MarkdownTestData.filePaths) {
                if (path.matches(regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText does not match\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/SubDirectory/NestedFile.md"
        ), list)
    }

    @Test fun test_linkRefMatcher_SubDirMulti_SmartExact() {
        val linkInfo = FileRef("/Users/vlad/src/MarkdownTest/Multiple-Match.md")
        val linkRef = LinkRef(linkInfo, "SubDirectory/Multiple-Match.md", null, null)

        val linkRefMatcher = GitHubLinkMatcher(projectResolver, linkRef, false)

        val list = ArrayList<String>()
        val matchText = linkRefMatcher.patternText(false)
        val matchWikiText = linkRefMatcher.patternText(true)
        val regexWiki = linkRefMatcher.patternRegex(true)
        val regex = linkRefMatcher.patternRegex(false)

        if (regex != null && regexWiki != null) {
            for (path in MarkdownTestData.filePaths) {
                val pathInfo = FileRef(path)
                if (path.matches(if (pathInfo.isWikiPage) regexWiki else regex)) {
                    list.add(path)
                }
            }
        }

        compareOrderedLists("$matchText\n$matchWikiText\n${linkInfo.filePath}\n", arrayListOf<String>(
                "/Users/vlad/src/MarkdownTest/SubDirectory/Multiple-Match.md"
        ), list)
    }
}

