package com.bkav.home.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.bkav.home.common.Input;
import com.bkav.home.common.Output;

public class SystemBoard {
	public SystemBoard() {
		this.globalInputs = new HashMap<String, InputHolder>();
		this.globalOutputs = new HashMap<String, OutputImpl>();
	}
	
	public void registerInput(Input input, String path) {
		InputHolder holder = getGlobalInput(path);
		holder.setInput(input);
	}
	
	public Output registerOutput(String path) {
		return getGlobalOutput(path);
	}
	
	public void connectInput(Output output, String inputPath) {
		if (output instanceof OutputImpl) {
			InputHolder holder = getGlobalInput(inputPath);
			OutputImpl outputImpl = (OutputImpl)output;
			outputImpl.addInput(holder);
		}
	}

	public void connectOutput(Input input, String outputPath) {
		OutputImpl outputImpl = getGlobalOutput(outputPath);
		outputImpl.addInput(input);
	}

	public void unreferInput(Input input) {
		Collection<InputHolder> holders = this.globalInputs.values();
		for (InputHolder holder: holders)
			if (holder.getInput() == input)
				holder.setInput(null);
		
		Collection<OutputImpl> outputs = this.globalOutputs.values();
		for (OutputImpl output: outputs) {
			output.removeInput(input);			
		}			
	}

	private HashMap<String, InputHolder> globalInputs;
	
	private HashMap<String, OutputImpl> globalOutputs;

	private InputHolder getGlobalInput(String path) {
		InputHolder holder = this.globalInputs.get(path);
		if (holder == null) {
			holder = new InputHolder();
			this.globalInputs.put(path, holder);
		}
		return holder;
	}
	
	private OutputImpl getGlobalOutput(String path) {
		OutputImpl output = this.globalOutputs.get(path);
		if (output == null) {
			output = new OutputImpl();
			this.globalOutputs.put(path, output);
		}
		return output;
	}
	
	
	private static class InputHolder implements Input {
		public InputHolder() {
			this.input = null;
		}
		
		public Input getInput() {
			return this.input;
		}
		
		public void setInput(Input input) {
			this.input = input;
		}
		
		@Override
		public void receive(int value) {
			if (this.input != null)
				this.input.receive(value);
		}		

		private Input input;
	}	
	
	private static class OutputImpl implements Output {
		public OutputImpl() {
			this.inputs = new ArrayList<Input>();
		}
		
		public void addInput(Input input) {
			if (!this.inputs.contains(input))
				this.inputs.add(input);
		}
		
		public void removeInput(Input input) {
			this.inputs.remove(input);
		}
		
		@Override
		public void send(int value) {
			for (Input input: this.inputs)
				input.receive(value);
		}
		
		private ArrayList<Input> inputs;		
	}
}
