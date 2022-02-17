import {UitkTextInput} from "@egencia/uitk-react-input";

export class InputExample extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            textdemo: ''
        };
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange(e) {
        this.setState({
            [e.target.name]: e.target.value
        });
    }
    render() {
        return (
            <UitkTextInput
                id="textdemo"
                label="React input example!"
                name="textdemo"
                value={this.state.textdemo}
                onChange={this.handleChange}
            />
        );
    }
}
