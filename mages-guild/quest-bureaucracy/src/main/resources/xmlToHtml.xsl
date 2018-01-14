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