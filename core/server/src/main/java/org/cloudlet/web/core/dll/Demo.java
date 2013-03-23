package org.cloudlet.web.core.dll;

/**
int RBCreateBook()
void RBFinishAndSave(int nBook,const WCHAR* wFilePath)
void RBSetScreenSize(int nBook,int cx,int cy)
void RBSetMargin(int nBook,int l,int t,int r,int b)
void RBAddFontStyle(int nBook,
const WCHAR* szStyleName,
const WCHAR* szCharAttrName,
const WCHAR* szFontFamily,
int nSize,
DWORD clr,
int bBold,
int bItalic)
void RBAddParaStyle(int nBook,
const WCHAR* szStyleName,
const WCHAR* szParaAttrName,
DWORD dwAlignMode,
int nPreLineSpace,
int nPostLineSpace,
int nPreParaSpace,
int nPostParaSpace)
int RBStartAppendSection(int nBook)
void RBEndAppendSection(int nBook,int nSection)
void RBSectionAppendSimplePara(int nSection,
  const WCHAR* szText,
  int len,
  const WCHAR* szFontStyle,
  const WCHAR* szParaStyle,
  int bCreateCatalog)
int RBSectionStartPara(int nSection)
void RBSectionEndPara(int nSection,int nPara,int bCreateCatalog)
void RBParaSetParaAttr(int nPara,const WCHAR* szParaStyle) 
void RBParaWriteText(int nPara,
const WCHAR* szText,
const WCHAR* szFontStyle,
const WCHAR* szLink)
void RBParaWriteImg(int nPara,
const WCHAR* szImg,
const WCHAR* szLink)
 */
public class Demo {
	static {
		System.loadLibrary("RichBookCvt");
	}

	public native static int RBCreateBook();

	public static void main(String[] args) {
		int i = RBCreateBook();
		System.out.println(i);
	}
}
