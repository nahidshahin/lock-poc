# lock-poc
poc on mule distributed lock

the counter app doesn't have any lock so it's failing to write in multi thread env.

Where as count-v2 has the distributed lock to fix this issue.