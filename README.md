# getDataFromUlk (legal info)

## Idea
Suppose you have a `source_table` in your vertica cluster with a column `field`.
And you would like to enrich the column sending values from it to  a `rest api`,
getting respone and saving it in a `dict_table`.

## Concept
First, scipt will create a dict table in vertica cluster if it not exists
Second, script run sql query like 
```sql
select
    source_table.field
from
    source_table
left join
    dict_table on source_table.field = dict_table.field
where
    dict_table.field is null
```

Then received values will be used to fetch data from api and save it do `dict_table`
 

## Usage
```bash
ApiRipper v.0.1
Usage: java - jar getDataFromUlk.jar [options]

  -d, --driver <value>     optional now only com.vertica.jdbc.Driver is supported
  -h, --host <value>       mandatory ip-address where db is hosted
  -p, --port <value>       mandatory db port
  -n, --dbName <value>     mandatory database name
  -u, --user <value>       mandatory database user
  --psw <value>            mandatory database user password
  -s, --schema <value>     optional schema name, public will be used by default
  -t, --table <value>      mandatory table name
  -f, --field <value>      mandatory field name in table to enrich
  -r, --restApi <value>    mandatory rest api uri
  --system <value>         mandatory system name
  --token <value>          mandatory system token
  -a, --authBearer <value>
                           mandatory authBearer
  --dictSchema <value>     mandatory schema name where result as dictionary will be saved
  --dictTable <value>      mandatory table name where result as dictionary will be saved. If table is not exist, it will be created
  --help                   print this usage text
```


## Build

```sbt assembly```

  





