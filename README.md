# Parser - позволяющий создать карту заданного сайта (список ссылок), и запиcать её в файл

## Что использовалось
*`Java` - SDK 17\
*`Maven` - сборка проекта\
*`ForkJoinPool` - многопоточный обход ("Разделяй и властвуй")\
*`Jsoup` - легкий парсинг HTML-страниц\
*`FileWriter` - класс с удобными методами для записи информации в файл
## Что умеет программа
- парсит сайт, извлекая только ссылки
- разделяет на потоки для быстрого обхода всех ссылок
- создает иерархию на дочерние ссылки относительно родительских
- записывает в файл полученные ссылки
## Пример результата записи в файл
![src/resources/photo_2024-04-26_23-22-23.jpg]()
