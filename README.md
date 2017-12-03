##Log parser task


The attached file contains HTTP web proxy log messages which details the HTTP activity of computers in the organization. Each line contains information about an HTTP access which includes, fields such as date, time, client IP, host, path, query, user agent and more.


The data is organized in the following way:

· Each line contains one event.

· The line is white space-delimited (a single or more space between one field to the other).

· Fields that contains space in their value are surrounded by double quotes.

· The order of the fields are stated at the beginning of the file, in a comment line started with the term #Fields:.

The goal of the program is to extract all host values, which are stated in a field called "cs-host", and count how many times each host appears in the file. The order of the output is by frequency in descending order. The result should be printed to the console. Example of such output should be:

###Example of output:

```
Host: www.google-analytics.com, Count: 24
Host: clients1.google.com, Count: 18
Host: www.google.com, Count: 16
Host: t0.gstatic.com, Count: 15
Host: 69.59.144.138, Count: 15
Host: t3.gstatic.com, Count: 15
Host: googleads.g.doubleclick.net, Count: 13
...
...
...
```

Please consider the following option – the results could be by demand also during the program run (meaning while it is still receiving logs)

Programming guidelines:

· Write the code in Java.

· Take design considerations that will take into account future support in different file format, input source (e.g. HTTP instead of file) etc.

· Performance is important. Use appropriate data structures, algorithms and avoid unnecessary code. Consider parsing as a bottle-neck

· Clean code with in-line comments that explain logic is appreciated.

· Cases and issues that are not covered by the program should be stated in TODO comments.

· Tests – unit and integration tests.

###Example of logs:

```
#Software: SGOS 5.4.3.7
#Version: 1.0
#Start-Date: 2011-08-04 21:00:00
#Date: 2011-08-02 11:15:58
#Fields: date time time-taken c-ip cs-username cs-auth-group x-exception-id sc-filter-result cs-categories cs(Referer) sc-status s-action cs-method rs(Content-Type) cs-uri-scheme cs-host cs-uri-port cs-uri-path cs-uri-query cs-uri-extension cs(User-Agent) s-ip sc-bytes cs-bytes x-virus-id
#Remark: 2610140037 "SG-42" "82.137.200.42" "main"
2011-08-04 21:00:00 11 0.0.0.0 - - - OBSERVED "unavailable" -  200 TCP_HIT GET application/octet-stream http kh.google.com 80 /flatfile ?f1-020300031202230-d.50200.391 - "GoogleEarth/6.0.3.2197(Windows;Microsoft Windows (5.1.2600.3);en-US;kml:2.2;client:Free;type:default)" 82.137.200.42 16827 406 -
2011-08-04 21:00:00 9311 0.0.0.0 - - - OBSERVED "unavailable" -  200 TCP_NC_MISS GET application/x-gzip http personal.avira-update.com 80 /update/wks_avira10/win32/en/pecl/en-us/avwin.chm.gz - gz "AntiVir-NGUpd/10.0.0.37 (PERS; WKS; EN; AVE 8.2.4.192; VDF 7.11.5.161; Windows 7; ; Syria; 273f27fe2dcb34b53c75441c4c0e56765a336283; 0000149996-ADJIE-0000001; SY; 10.0.0.648)" 82.137.200.42 875853 384 -
```