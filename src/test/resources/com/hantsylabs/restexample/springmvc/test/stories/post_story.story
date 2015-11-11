Feature: 
Post Features

Scenario: 
Add a new post

Given post title is Test Title and content is Test Content
When GET /api/posts
Then response status is 200
And response body contains Test Title
