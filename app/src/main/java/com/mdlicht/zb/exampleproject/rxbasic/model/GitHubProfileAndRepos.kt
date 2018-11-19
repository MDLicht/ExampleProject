package com.mdlicht.zb.exampleproject.rxbasic.model

data class GitHubProfileAndRepos(val profile: GitHubProfile, var repos: List<GitHubRepo>) {
    /**
     * @return GitHubProfileAndRepos object with Sorted by Star count
     */
    fun sortReposByStar() : GitHubProfileAndRepos {
        repos = repos.sortedByDescending {
            it.stargazers_count
        }
        return this
    }
}