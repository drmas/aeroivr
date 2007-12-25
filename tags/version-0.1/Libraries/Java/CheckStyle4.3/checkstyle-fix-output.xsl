<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="xml"/>

<xsl:template match="checkstyle">
    <xsl:element name="checkstyle">
       <xsl:copy-of select="@*"/>
       <xsl:apply-templates/>
    </xsl:element>
</xsl:template>

<xsl:template match="file">
    <xsl:element name="file">
       <xsl:copy-of select="@*"/>
        <xsl:apply-templates select="error"/>
    </xsl:element>
</xsl:template>


<xsl:template match="error">
        <xsl:choose>
                <xsl:when test="starts-with(@message,'Got an exception - java.lang.RuntimeException: Unable to get class information for')">
                        <xsl:apply-templates />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy-of select="."/>
                </xsl:otherwise>
        </xsl:choose>
</xsl:template>

</xsl:stylesheet>
