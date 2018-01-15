<?xml version="1.0" encoding="UTF-8"?>
<!--
    Description:
        Transforms a simple XML document (describing a table of data) into an HTML document.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"
                doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
                doctype-system="http://www.w3.org/TR/html4/loose.dtd"/>
    <xsl:template match="/table">
        <html>
            <head>
                <title><xsl:value-of select="/table/@identifier"/></title>
                <style type="text/css">
                    body {
                    font-family: sans-serif;
                    font-size: 11pt;
                    }
                    table {
                    border-collapse: collapse;
                    counter-reset: rownumber;
                    }
                    span.rowNumber:before {
                    counter-increment:rownumber;
                    content:counter(rownumber) ".";
                    }
                    th {
                    background-color: #cacaca;
                    }
                    td, th {
                    border: 1px solid #999;
                    padding: 2px 4px;
                    }
                    th.header {
                    background-repeat: no-repeat;
                    background-position: center right 2px;
                    cursor: pointer;
                    padding: 2px 13px 2px 4px;
                    }

                    tr:nth-child(odd) {
                    background-color: #e0e0e0;
                    }
                    tr:nth-child(even) {
                    background-color: #ffffff;
                    }

                    td.traditional {
                    background-color: #e0eee0 !important;
                    }
                    td.multi {
                    background-color: #eeeee0 !important;
                    }
                    td.mystery {
                    background-color: #e0e0ee !important;
                    }
                    <xsl:if test="boolean(/table/@identifier = 'caches')">
                        /* Coordinates and the size column: */
                        td:nth-child(5), td:nth-child(6), td:nth-child(7) {
                        white-space: nowrap;
                        }
                    </xsl:if>

                    span.why {
                    color: #0000ee;
                    border-bottom: 1px dotted #0000ee;
                    }

                    @media print {
                    .nonprintable {
                    display: none;
                    }

                    th {
                    background-image: none;
                    }
                    }
                </style>
            </head>
            <body>
                <table id="{@identifier}">
                    <thead>
                        <xsl:apply-templates select="row[@header='true']" />
                    </thead>
                    <tbody>
                        <xsl:apply-templates select="row[@header='false']" />
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="row">
        <tr>
            <xsl:choose>
                <xsl:when test="@header='true'">
                    <th>#</th>
                </xsl:when>
                <xsl:otherwise>
                    <td><span class="rowNumber"></span></td>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:apply-templates select="cell" />
        </tr>
    </xsl:template>

    <xsl:template match="cell">
                <xsl:choose>
                    <xsl:when test="../@header='true'">
                        <th><xsl:value-of select="." /></th>
                    </xsl:when>
                    <xsl:otherwise>
                        <td><xsl:value-of select="." /></td>
                    </xsl:otherwise>
                </xsl:choose>
    </xsl:template>
</xsl:stylesheet>