# riemann-tools

Programs to submit data to Riemann

## Usage

```
java -jar riemann-tools.jar --help

Usage: program-name [options] action

Options:
  -h, --host HOST              127.0.0.1                   Riemann host
  -p, --port PORT              5555                        Riemann port
  -e, --event-host EVENT_HOST  localhost/127.0.0.1         Event hostname
  -s, --solr-url SOLR_URL      http://localhost:8983/solr  Solr url
  -i, --interval INTERVAL      5                           Minutes between updates
  -m, --timeout TIMEOUT        30                          Timeout (in seconds) when waiting for acknowledgements
      --help                                               Show this message

Actions:
  solr    send Solr stats to Riemann
```

## License

Copyright © 2017

Distributed under the [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/) license.
