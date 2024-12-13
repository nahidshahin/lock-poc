# lock-poc
poc on mule distributed lock

the counter application doesn't have any lock so it's failing to write in multi-thread env.

On the other hand, the counter application version 2 (count-v2) has a distributed lock to fix this issue.
