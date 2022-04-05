# This is example of backend on scala  
## For this api I used play-framework

![](https://github.com/blackcater/blackcater/raw/main/images/Hi.gif)

## For start used `sbt "run 9000"` 

### For check if your service is Ok use:
`curl --location --request GET 'http://localhost:9000/api/health'`

### Add new book:
```
curl --location --request POST 'http://localhost:9000/api/books/add' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": "Harry Potter and the Philosopher's Stone",
"description": "Harry Potter has never..."
}'
```

### Get all books:
```
curl --location --request GET 'http://localhost:9000/api/books'
```

### Get book by Id:
```
curl --location --request GET 'http://localhost:9000/api/books/624c92024e01004e016a0b9d'
```

### Edit book by Id:
```
curl --location --request PUT 'http://localhost:9000/api/books/624c92024e01004e016a0b9d' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Harry Potter and the Philosopher'\''s Stone",
    "description": ""
    }'
```

### Delete books by Id:
```
curl --location --request DELETE 'http://localhost:9000/api/books/624c92024e01004e016a0b9d'
```
