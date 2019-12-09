import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INodeT } from 'app/shared/model/node-t.model';
import { getEntities as getNodeTs } from 'app/entities/node-t/node-t.reducer';
import { IPartT } from 'app/shared/model/part-t.model';
import { getEntities as getPartTs } from 'app/entities/part-t/part-t.reducer';
import { getEntity, updateEntity, createEntity, reset } from './module-t.reducer';
import { IModuleT } from 'app/shared/model/module-t.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IModuleTUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IModuleTUpdateState {
  isNew: boolean;
  idsnodeT: any[];
  partTId: string;
}

export class ModuleTUpdate extends React.Component<IModuleTUpdateProps, IModuleTUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsnodeT: [],
      partTId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getNodeTs();
    this.props.getPartTs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { moduleTEntity } = this.props;
      const entity = {
        ...moduleTEntity,
        ...values,
        nodeTS: mapIdList(values.nodeTS)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/module-t');
  };

  render() {
    const { moduleTEntity, nodeTS, partTS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="monoHeartApp.moduleT.home.createOrEditLabel">Create or edit a ModuleT</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : moduleTEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="module-t-id">ID</Label>
                    <AvInput id="module-t-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="module-t-name">
                    Name
                  </Label>
                  <AvField id="module-t-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="module-t-nodeT">Node T</Label>
                  <AvInput
                    id="module-t-nodeT"
                    type="select"
                    multiple
                    className="form-control"
                    name="nodeTS"
                    value={moduleTEntity.nodeTS && moduleTEntity.nodeTS.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {nodeTS
                      ? nodeTS.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/module-t" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  nodeTS: storeState.nodeT.entities,
  partTS: storeState.partT.entities,
  moduleTEntity: storeState.moduleT.entity,
  loading: storeState.moduleT.loading,
  updating: storeState.moduleT.updating,
  updateSuccess: storeState.moduleT.updateSuccess
});

const mapDispatchToProps = {
  getNodeTs,
  getPartTs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ModuleTUpdate);
