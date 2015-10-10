/*
 * Copyright (c) 2015-2015 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.vladsch.idea.multimarkdown.util;

import com.intellij.openapi.vfs.VirtualFile;
import com.vladsch.idea.multimarkdown.MultiMarkdownFileTypeFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FilePathInfo implements Comparable<FilePathInfo> {
    public static final String WIKI_PAGE_EXTENSION = ".md";
    public static final String WIKI_HOME_EXTENTION = ".wiki";
    public static final String WIKI_HOME_NAME = "wiki";
    public static final String WIKI_LINK_REF_PREFIX = "../../wiki/";
    public static final String[] IMAGE_EXTENSIONS = { "png", "jpg", "jpeg", "gif", };
    public static final String[] MARKDOWN_EXTENSIONS = MultiMarkdownFileTypeFactory.EXTENSIONS;
    public static final String[] WIKI_PAGE_EXTENSIONS = { MultiMarkdownFileTypeFactory.DEFAULT_EXTENSION };

    private final int wikiHomeEnd;
    private final int nameStart;
    private final int nameEnd;
    private final int withAnchorNameEnd;
    private final int anchorStart;
    private final @NotNull String filePath;

    public FilePathInfo(@NotNull String filePath) {
        if (!filePath.isEmpty() && filePath.charAt(filePath.length() - 1) == '/') {
            filePath = filePath.substring(0, filePath.length() - 1);
        }

        this.filePath = filePath;
        int lastSep;
        this.nameStart = (lastSep = filePath.lastIndexOf('/')) < 0 ? 0 : (lastSep == filePath.length() - 1 ? lastSep : lastSep + 1);

        int ghWikiHomeEnd = (ghWikiHomeEnd = filePath.lastIndexOf(WIKI_HOME_NAME + "/", nameStart)) >= nameStart || ghWikiHomeEnd < 0 || (ghWikiHomeEnd > 0 && filePath.charAt(ghWikiHomeEnd - 1) != '/') ? 0 : ghWikiHomeEnd + WIKI_HOME_NAME.length();

        int wikiHomeEnd = (wikiHomeEnd = filePath.lastIndexOf(WIKI_HOME_EXTENTION + "/", nameStart)) >= nameStart || wikiHomeEnd < 0 ? 0 : wikiHomeEnd + WIKI_HOME_EXTENTION.length();

        this.wikiHomeEnd = wikiHomeEnd > ghWikiHomeEnd ? wikiHomeEnd : ghWikiHomeEnd;

        // if file name ends in . then it has no extension and the . is part of its name.
        int extStart;
        this.anchorStart = (extStart = filePath.indexOf('#', nameStart)) < 0 ? filePath.length() : extStart;
        this.nameEnd = (extStart = filePath.lastIndexOf('.', anchorStart)) <= nameStart ? anchorStart : extStart;
        this.withAnchorNameEnd = (extStart = filePath.lastIndexOf('.', filePath.length())) <= nameStart ? filePath.length() : extStart;
    }

    public FilePathInfo(@NotNull VirtualFile file) {
        this(file.getPath());
    }

    public FilePathInfo(@NotNull FilePathInfo other) {
        this.wikiHomeEnd = other.wikiHomeEnd;
        this.nameStart = other.nameStart;
        this.nameEnd = other.nameEnd;
        this.filePath = other.filePath;
        this.anchorStart = other.anchorStart;
        this.withAnchorNameEnd = other.withAnchorNameEnd;
    }

    @NotNull
    final public String getExt() {
        return nameEnd + 1 < anchorStart ? filePath.substring(nameEnd + 1, anchorStart) : "";
    }

    @NotNull
    final public String getExtWithDot() {
        return nameEnd < anchorStart ? filePath.substring(nameEnd, anchorStart) : "";
    }

    @NotNull
    final public String getWithAnchorExt() {
        return fileNameContainsAnchor() && withAnchorNameEnd + 1 < filePath.length() ? filePath.substring(withAnchorNameEnd + 1) : getExt();
    }

    @NotNull
    final public String getWithAnchorExtWithDot() {
        return fileNameContainsAnchor() && withAnchorNameEnd < filePath.length() ? filePath.substring(withAnchorNameEnd) : getExtWithDot();
    }

    @NotNull
    final public String getAnchorNoHash() {
        return anchorStart + 1 < filePath.length() ? filePath.substring(anchorStart + 1) : "";
    }

    @NotNull
    final public String getAnchor() {
        return anchorStart < filePath.length() ? filePath.substring(anchorStart) : "";
    }

    final public boolean isImageExt() {
        return isImageExt(getExt());
    }

    final public boolean isMarkdownExt() {
        return isMarkdownExt(getExt());
    }

    public boolean hasExt() {
        return nameEnd + 1 < anchorStart;
    }

    final public boolean hasWikiPageExt() {
        return getFilePath().endsWith(WIKI_PAGE_EXTENSION);
    }

    @NotNull
    public String getFilePath() {
        return filePath.substring(0, anchorStart);
    }

    @NotNull
    final public String getFilePathWithAnchor() {
        return filePath;
    }

    @NotNull
    final public String getFilePathAsWikiRef() {
        return asWikiRef(getFilePath());
    }

    @NotNull
    final public String getFilePathWithAnchorAsWikiRef() {
        return asWikiRef(getFilePath()) + getAnchor();
    }

    final public boolean containsSpaces() {
        return filePath.indexOf(' ') >= 0;
    }

    final public boolean containsAnchor() {
        return filePath.indexOf('#') >= 0;
    }

    final public boolean isWikiHome() {
        return filePath.endsWith(WIKI_HOME_EXTENTION) || getFileName().equals(WIKI_HOME_NAME);
    }

    @NotNull
    final public String getFilePathNoExt() {
        return filePath.substring(0, nameEnd);
    }

    @NotNull
    final public String getFilePathWithAnchorNoExt() {
        return filePath.substring(0, withAnchorNameEnd);
    }

    @NotNull
    final public String getFilePathNoExtAsWikiRef() {
        return asWikiRef(getFilePathNoExt());
    }

    @NotNull
    final public String getFilePathWithAnchorNoExtAsWikiRef() {
        return asWikiRef(getFilePathWithAnchorNoExt());
    }

    @NotNull
    final public String getPath() {
        return nameStart == 0 ? "" : filePath.substring(0, nameStart);
    }

    @NotNull
    final public String getPathAsWikiRef() {
        return asWikiRef(getPath());
    }

    final public boolean isUnderWikiHome() {
        return wikiHomeEnd > 0;
    }

    final public boolean isWikiPage() {
        return isUnderWikiHome() && isWikiPageExt(getExt());
    }

    @NotNull
    final public String getWikiHome() {
        return wikiHomeEnd <= 0 ? "" : filePath.substring(0, wikiHomeEnd);
    }

    public String getLinkRefFromWikiHome() {
        return wikiHomeEnd <= 0 ? filePath : filePath.substring(wikiHomeEnd+1);
    }

    final public int getUpDirectoriesToWikiHome() {
        if (wikiHomeEnd <= 0 || wikiHomeEnd == filePath.length()) return 0;
        int pos = wikiHomeEnd;
        int upDirs = 0;
        while (pos < filePath.length() && (pos = filePath.indexOf('/', pos)) >= 0) {
            upDirs++;
            pos++;
        }
        return upDirs;
    }

    final public boolean pathContainsSpaces() {
        return getPath().indexOf(' ') >= 0;
    }

    final public boolean pathContainsAnchor() {
        return getPath().indexOf('#') >= 0;
    }

    @NotNull
    final public String getFileNameWithAnchor() {
        return filePath.substring(nameStart, filePath.length());
    }

    @NotNull
    final public String getFileName() {
        return filePath.substring(nameStart, anchorStart);
    }

    final public boolean fileNameContainsSpaces() {
        return getFileName().indexOf(' ') >= 0;
    }

    final public boolean fileNameContainsAnchor() {
        return anchorStart < filePath.length();
    }

    @NotNull
    final public String getFileNameAsWikiRef() {
        return asWikiRef(getFileName());
    }

    @NotNull
    final public String getFileNameWithAnchorAsWikiRef() {
        return asWikiRef(getFileNameWithAnchor());
    }

    @NotNull
    final public String getFileNameNoExt() {
        return filePath.substring(nameStart, nameEnd);
    }

    @NotNull
    final public String getFileNameWithAnchorNoExt() {
        return filePath.substring(nameStart, withAnchorNameEnd);
    }

    @NotNull
    final public String getFileNameNoExtAsWikiRef() {
        return asWikiRef(getFileNameNoExt());
    }

    @NotNull
    final public String getFileNameWithAnchorNoExtAsWikiRef() {
        return asWikiRef(getFileNameWithAnchorNoExt());
    }

    @Override
    public int compareTo(FilePathInfo o) {
        return wikiHomeEnd == o.wikiHomeEnd &&
                nameStart == o.nameStart &&
                nameEnd == o.nameEnd ? filePath.compareTo(o.filePath) : -99;
    }

    @Override
    public String toString() {
        return "FilePathInfo(" +
                innerString() +
                ")";
    }

    public String innerString() {
        return "" +
                "wikiHomeEnd = " + wikiHomeEnd + ", " +
                "nameStart = " + nameStart + ", " +
                "nameEnd = " + nameEnd + ", " +
                "filePath = " + "'" + filePath + "', " +
                "";
    }

    public boolean isEmpty() {
        return filePath.isEmpty();
    }

    public boolean isRoot() {
        return filePath.equals("/");
    }

    public boolean isRelative() {
        return filePath.isEmpty() || filePath.charAt(0) != '/';
    }

    public FilePathInfo append(String... parts) {
        StringBuilder path = new StringBuilder(filePath.length() + 100);
        path.append(filePath);

        for (String part : parts) {
            if (part != null && !part.isEmpty()) {
                if (path.length() == 0 || path.charAt(path.length() - 1) != '/') path.append('/');
                path.append(part);
            }
        }
        return new FilePathInfo(path.toString());
    }

    @Nullable
    public FilePathInfo resolveLinkRef(@Nullable String linkRef, boolean convertGitHubWikiHome, boolean withAnchor) {
        String cleanLinkRef = linkRef;
        if (cleanLinkRef == null || cleanLinkRef.isEmpty()) return null;

        if (!withAnchor) {
            cleanLinkRef = new FilePathInfo(linkRef).getFilePath();
        }

        if (cleanLinkRef.charAt(0) == '/') return new FilePathInfo(cleanLinkRef);

        String[] linkRefParts = cleanLinkRef.split("/");
        FilePathInfo pathInfo = new FilePathInfo(getPath());

        int iMax = linkRefParts.length;
        for (int i = 0; i < iMax; i++) {
            if (linkRefParts[i].equals(".")) continue;
            if (linkRefParts[i].equals("..")) {
                if (pathInfo.isEmpty() || pathInfo.isRoot()) return null;

                if (convertGitHubWikiHome) {
                    if (pathInfo.isWikiHome()) {
                        if (i + 1 < iMax && linkRefParts[i + 1].equals(WIKI_HOME_NAME)) {
                            // if this is a wiki home and what comes next is 'wiki' then we can replace it with nothing, it is just the current path
                            i += 1;
                            continue;
                        }
                    } else {
                        if (i + 2 < iMax && linkRefParts[i + 1].equals("..") && linkRefParts[i + 2].equals(WIKI_HOME_NAME)) {
                            // if this is not a wiki home and what comes next is ../wiki then we can replace is a subdirectory with current dir name with .wiki added
                            pathInfo = pathInfo.append(pathInfo.getFileName() + WIKI_HOME_EXTENTION);
                            i += 2;
                            continue;
                        }
                    }
                }
                pathInfo = new FilePathInfo(pathInfo.getPath());
            } else {
                pathInfo = pathInfo.append(linkRefParts[i]);
            }
        }
        return pathInfo;
    }

    @Nullable
    public FilePathInfo resolveLinkRef(@Nullable String linkRef, boolean convertGitHubWikiHome) {
        return resolveLinkRef(linkRef, convertGitHubWikiHome, false);
    }

    @Nullable
    public FilePathInfo resolveLinkRefWithAnchor(@Nullable String linkRef, boolean convertGitHubWikiHome) {
        return resolveLinkRef(linkRef, convertGitHubWikiHome, true);
    }

    @Nullable
    public FilePathInfo resolveLinkRef(@Nullable String linkRef) {
        return resolveLinkRef(linkRef, false, false);
    }

    @Nullable
    public FilePathInfo resolveLinkRefWithAnchor(@Nullable String linkRef) {
        return resolveLinkRef(linkRef, false, true);
    }

    @Nullable
    public FilePathInfo resolveLinkRefToWikiPage(@Nullable String linkRef) {
        return resolveLinkRef(linkRef, true, false);
    }

    @Nullable
    public FilePathInfo resolveLinkRefWithAnchorToWikiPage(@Nullable String linkRef) {
        return resolveLinkRef(linkRef, true, true);
    }

    /*
     *
     * Statics
     *
     */

    public static boolean isExtInList(boolean caseSensitive, @NotNull String ext, String... extList) {
        for (String listExt : extList) {
            if (caseSensitive ? listExt.equals(ext) : listExt.equalsIgnoreCase(ext)) return true;
        }
        return false;
    }

    @NotNull
    public static String asWikiRef(@NotNull String filePath) {
        return filePath.replace('-', ' ');
    }

    @NotNull
    public static String asFileRef(@NotNull String filePath) {
        return filePath.replace(' ', '-');
    }

    protected static boolean compare(boolean forWikiRef, boolean caseSensitive, boolean spaceDashEquivalent, int i, int iMax, @NotNull String fileRef, int fileRefOffs, @NotNull String linkRef, int linkRefOffs) {
        if (forWikiRef) {
            for (; i < iMax; i++) {
                char wC = linkRef.charAt(i + linkRefOffs);
                char fC = fileRef.charAt(i + fileRefOffs);
                if (fC == '-') return false;
                if (wC == '-' && !spaceDashEquivalent) return false;

                if (wC == fC) continue;
                if (fC == ' ' && wC == '-') continue;
                if (!caseSensitive && Character.toLowerCase(fC) == Character.toLowerCase(wC)) continue;
                return false;
            }
        } else {
            for (; i < iMax; i++) {
                char wC = linkRef.charAt(i + linkRefOffs);
                char fC = fileRef.charAt(i + fileRefOffs);
                if (wC == fC) continue;
                if (!caseSensitive && Character.toLowerCase(fC) == Character.toLowerCase(wC)) continue;
                if (spaceDashEquivalent && fC == '-' && wC == ' ') continue;
                return false;
            }
        }
        return true;
    }

    @NotNull
    public static String removeDotDirectory(@NotNull String linkRef) {
        linkRef = linkRef.replace("/./", "/");

        if (linkRef.startsWith("./")) {
            linkRef = linkRef.substring(2);
        }
        return linkRef;
    }

    private static boolean endsWith(boolean forWikiRef, boolean caseSensitive, boolean spaceDashEquivalent, @NotNull String fileRef, @NotNull String linkRef) {
        linkRef = removeDotDirectory(linkRef);
        if (!forWikiRef && !spaceDashEquivalent && caseSensitive) {
            return fileRef.endsWith(linkRef);
        }

        int linkRefLen = linkRef.length();
        int fileRefLen = fileRef.length();
        int fileRefOffs = fileRefLen - linkRefLen;

        return fileRefLen >= linkRefLen && compare(forWikiRef, caseSensitive, spaceDashEquivalent, 0, linkRefLen, fileRef, fileRefOffs, linkRef, 0);
    }

    private static boolean equivalent(boolean forWikiRef, boolean caseSensitive, boolean spaceDashEquivalent, @NotNull String fileRef, @NotNull String linkRef) {
        linkRef = removeDotDirectory(linkRef);
        if (!forWikiRef && !spaceDashEquivalent) {
            return caseSensitive ? fileRef.equals(linkRef) : fileRef.equalsIgnoreCase(linkRef);
        }

        int linkRefLen = linkRef.length();
        int fileRefLen = fileRef.length();

        return fileRefLen == linkRefLen && compare(forWikiRef, caseSensitive, spaceDashEquivalent, 0, linkRefLen, fileRef, 0, linkRef, 0);
    }

    public static boolean equivalent(boolean caseSensitive, boolean spaceDashEquivalent, @NotNull String fileRef, @NotNull String linkRef) {
        return equivalent(false, caseSensitive, spaceDashEquivalent, fileRef, linkRef);
    }

    public static boolean endsWith(boolean caseSensitive, boolean spaceDashEquivalent, @NotNull String fileRef, @NotNull String linkRef) {
        return endsWith(false, caseSensitive, spaceDashEquivalent, fileRef, linkRef);
    }

    public static boolean equivalentWikiRef(boolean caseSensitive, boolean spaceDashEquivalent, @NotNull String fileRef, @NotNull String wikiRef) {
        return equivalent(true, caseSensitive, spaceDashEquivalent, fileRef, wikiRef);
    }

    public static boolean endsWithWikiRef(boolean caseSensitive, boolean spaceDashEquivalent, @NotNull String fileRef, @NotNull String wikiRef) {
        return endsWith(true, caseSensitive, spaceDashEquivalent, fileRef, wikiRef);
    }

    // TEST: needs a test
    @NotNull
    public static String linkRefNoAnchor(@Nullable String linkRef) {
        if (linkRef != null) {
            int pos;
            // Links can have anchor # refs
            if ((pos = linkRef.indexOf("#")) >= 0) {
                linkRef = linkRef.substring(0, pos);
            }
        }
        return linkRef == null ? "" : linkRef;
    }

    // TEST: needs a test
    @NotNull
    public static String linkRefAnchor(@Nullable String linkRef) {
        if (linkRef != null) {
            int pos;
            // Links can have anchor # refs
            if ((pos = linkRef.indexOf("#")) >= 0) {
                linkRef = linkRef.substring(pos);
            } else {
                linkRef = "";
            }
        }
        return linkRef == null ? "" : linkRef;
    }

    // TEST: needs a test
    @NotNull
    public static String linkRefAnchorNoHash(@Nullable String linkRef) {
        linkRef = linkRefAnchor(linkRef);
        return linkRef.isEmpty() ? linkRef : linkRef.charAt(0) == '#' ? linkRef.substring(1) : linkRef;
    }

    public static boolean isExtInList(@NotNull String ext, String... extList) {
        return isExtInList(false, ext, extList);
    }

    public static boolean isExtInListCaseSensitive(@NotNull String ext, String... extList) {
        return isExtInList(true, ext, extList);
    }

    public static boolean isImageExt(@NotNull String ext) {
        return isExtInList(false, ext, IMAGE_EXTENSIONS);
    }

    public static boolean isMarkdownExt(@NotNull String ext) {
        return isExtInList(false, ext, MARKDOWN_EXTENSIONS);
    }

    public static boolean isWikiPageExt(@NotNull String ext) {
        return isExtInList(false, ext, WIKI_PAGE_EXTENSIONS);
    }

    public static String wikiRefAsFileName(String name, boolean addExtension) {
        return name.replace(' ', '-') + (addExtension ? WIKI_PAGE_EXTENSION : "");
    }

    public static String wikiRefAsFileNameNoExt(String name) {
        return wikiRefAsFileName(name, false);
    }

    public static String wikiRefAsFileNameWithExt(String name) {
        return wikiRefAsFileName(name, true);
    }

    // TEST: needs a test
    public static String endWith(@Nullable String dir, char c) {
        if (dir != null && (dir.isEmpty() || dir.charAt(dir.length() - 1) != c)) {
            return dir + c;
        }
        return dir;
    }

    // TEST: needs a test
    public static String removeEnd(@Nullable String dir, char c) {
        if (dir != null && (!dir.isEmpty() && dir.charAt(dir.length() - 1) == c)) {
            return dir.substring(0, dir.length() - 1);
        }
        return dir;
    }

    // TEST: needs a test
    public static String startWith(@Nullable String dir, char c) {
        if (dir != null && (dir.isEmpty() || dir.charAt(0) != c)) {
            return dir + c;
        }
        return dir;
    }

    // TEST: needs a test
    public static String removeStart(@Nullable String dir, char c) {
        if (dir != null && (!dir.isEmpty() && dir.charAt(0) == c)) {
            return dir.substring(1);
        }
        return dir;
    }

    // TEST: needs a test
    public static int countOccurrence(@Nullable String dir, char c) {
        int count = 0;
        if (dir != null) {
            int pos = 0;
            while ((pos = dir.indexOf(c, pos)) >= 0) {
                count++;
                pos++;
            }
        }
        return count;
    }

    // TEST: needs a test
    public static int countOccurrence(@Nullable String dir, @NotNull String s) {
        int count = 0;
        if (dir != null) {
            int pos = 0;
            while ((pos = dir.indexOf(s, pos)) >= 0) {
                count++;
                pos += s.length();
            }
        }
        return count;
    }
}
