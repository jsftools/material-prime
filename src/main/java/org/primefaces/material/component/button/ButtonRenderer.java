package org.primefaces.material.component.button;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.commandbutton.CommandButtonRenderer;
import org.primefaces.material.MaterialPrime;
import org.primefaces.material.MaterialWidgetBuilder;
import org.primefaces.util.HTML;
import org.primefaces.util.WidgetBuilder;

public class ButtonRenderer extends CommandButtonRenderer{
	
	public static final String RENDERER_TYPE = "org.primefaces.material.component.ButtonRenderer";
	
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Button button = (Button) component;
		
		encodeMarkup(context, button);
		encodeScript(context, button);
	}
	
	protected void encodeScript(FacesContext context, Button button) throws IOException {
		String clientId = button.getClientId();
		String widgetVar = button.resolveWidgetVar();
		 
		WidgetBuilder wb = MaterialWidgetBuilder.getInstance(context);
		 
		wb.initWithDomReady("Button", widgetVar, clientId);
		 
		wb.finish();
	}
	
	protected void encodeMarkup(FacesContext context, Button button) throws IOException {
		
		ResponseWriter writer = context.getResponseWriter();
		
		boolean pushButton = (button.getType().equals("reset")||button.getType().equals("button"));
        String request = pushButton ? null: buildRequest(context, button, button.getClientId());        
        String onclick = buildDomEvent(context, button, "onclick", "click", "action", request);		
		String buttonClass = getButtonClass(button);
		boolean hasText = button.getValue() != null;
		boolean hasIcon = button.getIcon() != null;
		
		writer.startElement("button", button);		
			writer.writeAttribute("id", button.getClientId(), null);
			writer.writeAttribute("class", buttonClass , null);	
			writer.writeAttribute("onclick", onclick, "onclick");
			renderPassThruAttributes(context, button, HTML.BUTTON_ATTRS, HTML.CLICK_EVENT);

	        if(button.isDisabled()) writer.writeAttribute("disabled", "disabled", "disabled");
	        if(button.isReadonly()) writer.writeAttribute("readonly", "readonly", "readonly");
	        
	        if(hasIcon && button.getIconPos().equals("left")){
	        	writer.startElement("i", null);
					writer.writeAttribute("id", button.getClientId()+"_icon", null);
					writer.writeAttribute("class", button.getIcon(), null);
					if(hasText){
						writer.writeAttribute("style", "margin-right:5px", null);
					}
				writer.endElement("i");
	        }
	        
	        if(button.getValue() != null){
	        	writer.write(button.getValue().toString());
	        }
	        
	        if(hasIcon && button.getIconPos().equals("right")){
	        	writer.startElement("i", null);
					writer.writeAttribute("id", button.getClientId()+"_icon", null);
					writer.writeAttribute("class", button.getIcon(), null);
					if(hasText){
						writer.writeAttribute("style", "margin-left:5px", null);
					}
				writer.endElement("i");
	        }
	   writer.endElement("button");
			
	}

	private String getButtonClass(Button button) {
		String btnClass = "btn";
		
		String buttonLook = button.getLook() != null ? button.getLook().toLowerCase() : "default";
		if(!MaterialPrime.MATERIAL_LOOKS.contains(buttonLook)){
			buttonLook = "default";
		}
		
		btnClass += " btn-" + buttonLook;
		
		String buttonLevel = button.getLevel() != null ? button.getLevel().toLowerCase() : "default";
		if(Button.BUTTON_LEVELS.contains(buttonLevel)){
			btnClass += " btn-" + buttonLevel;
		}
		
		String buttonSize = button.getSize() != null ? button.getSize().toLowerCase() : "default";
		if(Button.BUTTON_SIZES.containsKey(buttonSize)){
			btnClass += " btn-" + Button.BUTTON_SIZES.get(button.getSize());
		}
		
		return btnClass;
	}
	
}
